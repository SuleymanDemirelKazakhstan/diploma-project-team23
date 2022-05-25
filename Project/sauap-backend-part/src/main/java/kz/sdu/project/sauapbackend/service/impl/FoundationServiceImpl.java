package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.dto.FoundationDto;
import kz.sdu.project.sauapbackend.dto.FoundationRoles;
import kz.sdu.project.sauapbackend.dto.request.CreateFoundationDto;
import kz.sdu.project.sauapbackend.dto.request.UpdateFoundationRequestDto;
import kz.sdu.project.sauapbackend.dto.response.FoundationDetailsResponseDto;
import kz.sdu.project.sauapbackend.dto.response.MainCreateResponse;
import kz.sdu.project.sauapbackend.dto.response.UpdateImageResponseDto;
import kz.sdu.project.sauapbackend.entity.Foundation;
import kz.sdu.project.sauapbackend.entity.FoundationCredits;
import kz.sdu.project.sauapbackend.entity.FoundationHasUsers;
import kz.sdu.project.sauapbackend.entity.Users;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.mapper.FoundationMapper;
import kz.sdu.project.sauapbackend.repository.FoundationCreditsRepository;
import kz.sdu.project.sauapbackend.repository.FoundationHasUsersRepository;
import kz.sdu.project.sauapbackend.repository.FoundationRepository;
import kz.sdu.project.sauapbackend.repository.UsersRepository;
import kz.sdu.project.sauapbackend.service.AmazonS3Service;
import kz.sdu.project.sauapbackend.service.FoundationService;
import kz.sdu.project.sauapbackend.service.ValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Primary
public class FoundationServiceImpl implements FoundationService {

    private final FoundationRepository foundationRepository;
    private final UsersRepository usersRepository;
    private final FoundationCreditsRepository foundationCreditsRepository;
    private final FoundationHasUsersRepository foundationHasUsersRepository;
    private final ValidatorService<CreateFoundationDto> validator;
    private final AmazonS3Service amazonS3Service;

    private static final String FOUNDATION_DOCS_FOLDER = "/foundation/docs";
    private static final String FOUNDATION_IMAGES_FOLDER = "/foundation/images";

    public FoundationServiceImpl(FoundationRepository foundationRepository,
                                 UsersRepository usersRepository,
                                 FoundationCreditsRepository foundationCreditsRepository,
                                 FoundationHasUsersRepository foundationHasUsersRepository,
                                 ValidatorService<CreateFoundationDto> validator,
                                 @Qualifier("ProcessDocumentsServiceImpl") AmazonS3Service amazonS3Service) {
        this.foundationRepository = foundationRepository;
        this.usersRepository = usersRepository;
        this.foundationCreditsRepository = foundationCreditsRepository;
        this.foundationHasUsersRepository = foundationHasUsersRepository;
        this.validator = validator;
        this.amazonS3Service = amazonS3Service;
    }

    @Override
    public Optional<List<FoundationDto>> getAllFoundations() {
        List<Foundation> allFoundations = foundationRepository.findAll();
        if (allFoundations.isEmpty()) {
            return Optional.empty();
        }
        List<FoundationDto> foundationDtos = allFoundations.stream()
                .filter(Foundation::getIsApproved)
                .map(FoundationMapper::toDto)
                .collect(Collectors.toList());
        return Optional.of(foundationDtos);
    }

    @Override
    public FoundationDetailsResponseDto getFoundationById(Long foundationId) {
        return getFoundationById(foundationId, true);
    }

    @Override
    public FoundationDetailsResponseDto getUserFoundations(Long userId) {
        Optional<Users> optionalUser = usersRepository.findById(userId);
        if (optionalUser.isPresent()) {
            List<FoundationHasUsers> heads = foundationHasUsersRepository.findFoundationHasUsersByUserIdAndRole(userId, FoundationRoles.HEAD.name());
            if (heads.isEmpty()) {
                throw new ValidationException("Foundation for this user not found", ErrorCode.FOUNDATION_NOT_FOUND);
            }
            FoundationHasUsers head = heads.stream().findFirst().get();
            return getFoundationById(head.getFoundationId(), true);
        }
        throw new ValidationException("User is not found", ErrorCode.USER_NOT_FOUND);
    }

    @Override
    public MainCreateResponse requestToCreateNewFoundation(CreateFoundationDto createFoundationDto, MultipartFile documentFile, Long userId) {
        /* validate coming request */
        validator.validate(createFoundationDto);

        Optional<Users> checkUser = usersRepository.findById(userId);
        if (checkUser.isEmpty()) {
            throw new ValidationException("User is not found or doesn't exist", ErrorCode.USER_NOT_FOUND);
        }

        // save document to amazon S3 cloud
        String documentUrl = amazonS3Service.uploadFile(documentFile, FOUNDATION_DOCS_FOLDER);
        log.info("CREATE FOUNDATION REQUEST | New foundation document saved successfully | {} | Document: {}", createFoundationDto, documentFile.getOriginalFilename());

        try {
            Foundation newFoundation = new Foundation();
            newFoundation.setIsApproved(false);
            newFoundation.setCity(createFoundationDto.getCity());
            newFoundation.setFoundationName(createFoundationDto.getFoundationName());
            newFoundation.setBin(createFoundationDto.getBin());
            newFoundation.setContactName(createFoundationDto.getContactName());
            newFoundation.setPhoneNumber(createFoundationDto.getPhoneNumber());
            newFoundation.setDocumentLink(documentUrl);

            //save foundation
            Foundation savedFoundation = foundationRepository.saveAndFlush(newFoundation);

            FoundationCredits newFoundationCredit = new FoundationCredits();
            newFoundationCredit.setFoundationId(savedFoundation.getFoundationId());
            newFoundationCredit.setBin(createFoundationDto.getCredits().getBin());
            newFoundationCredit.setKbe(createFoundationDto.getCredits().getKbe());
            newFoundationCredit.setKnp(createFoundationDto.getCredits().getKnp());
            newFoundationCredit.setBic(createFoundationDto.getCredits().getBic());
            newFoundationCredit.setAccount(createFoundationDto.getCredits().getAccount());
            newFoundationCredit.setLegalAddress(createFoundationDto.getCredits().getLegalAddress());

            //save foundation credit
            FoundationCredits savedFoundationCredit = foundationCreditsRepository.saveAndFlush(newFoundationCredit);

            //save foundation has users
            FoundationHasUsers newFoundationUser = new FoundationHasUsers();
            newFoundationUser.setFoundationId(savedFoundationCredit.getFoundationId());
            newFoundationUser.setUserId(userId);
            newFoundationUser.setIsSubscribed(true);
            newFoundationUser.setRole(FoundationRoles.HEAD.name());

            FoundationHasUsers savedFoundationUser = foundationHasUsersRepository.saveAndFlush(newFoundationUser);
            log.info("CREATE FOUNDATION REQUEST | New foundation request created | {}, {}, {}", savedFoundation, savedFoundationCredit, savedFoundationUser);

            return new MainCreateResponse(savedFoundation.getFoundationId());
        } catch (Exception ex) {
            amazonS3Service.deleteFile(documentFile.getOriginalFilename(), FOUNDATION_DOCS_FOLDER);
            throw ex;
        }
    }

    @Override
    public UpdateImageResponseDto updateFoundationImage(Long foundationId, MultipartFile imageFile) {
        Optional<Foundation> foundationOptional = foundationRepository.findById(foundationId);
        String photoLink;
        if (foundationOptional.isPresent()) {
            Foundation foundation = foundationOptional.get();
            if (!Objects.isNull(foundation.getPhotoLink())) {
                String fileName = foundation.getPhotoLink().replaceAll(FOUNDATION_IMAGES_FOLDER + "/", "");
                amazonS3Service.deleteFile(fileName, FOUNDATION_IMAGES_FOLDER);
            }
            photoLink = amazonS3Service.uploadFile(imageFile, FOUNDATION_IMAGES_FOLDER);
            foundation.setPhotoLink(photoLink);
            foundationRepository.saveAndFlush(foundation);

            return new UpdateImageResponseDto(photoLink);
        }
        throw new ValidationException("Foundation is not found", ErrorCode.FOUNDATION_NOT_FOUND);
    }

    @Override
    public void update(UpdateFoundationRequestDto foundation, Long foundationId) {
        Optional<Foundation> foundationOptional = foundationRepository.findById(foundationId);
        if (foundationOptional.isPresent()) {
            Foundation foundationFromDb = foundationOptional.get();

            if (Objects.nonNull(foundation.getAddress())) {
                foundationFromDb.setAddress(foundation.getAddress());
            }
            if (Objects.nonNull(foundation.getDescription())) {
                foundationFromDb.setDescription(foundation.getDescription());
            }
            if (Objects.nonNull(foundation.getName())) {
                foundationFromDb.setFoundationName(foundation.getName());
            }
            if (Objects.nonNull(foundation.getPhone())) {
                foundationFromDb.setPhoneNumber(foundation.getPhone());
            }
            if (Objects.nonNull(foundation.getWebsite())) {
                foundationFromDb.setWebsiteUrl(foundation.getWebsite());
            }

            foundationRepository.saveAndFlush(foundationFromDb);
            log.info("UPDATE FOUNDATION INFO | updated successfully | {}, {}", foundation, foundationFromDb);
            return;
        }
        throw new ValidationException("Foundation is not found", ErrorCode.FOUNDATION_NOT_FOUND);
    }

    private FoundationDetailsResponseDto getFoundationById(Long foundationId, boolean isApproved) {
        Optional<Foundation> foundationOptional = foundationRepository.findByFoundationIdAndIsApproved(foundationId, isApproved);
        if (foundationOptional.isPresent()) {
            FoundationDetailsResponseDto foundationDetail = FoundationMapper.toDetailedDto(foundationOptional.get());
            List<FoundationHasUsers> foundationHasUsers = foundationHasUsersRepository.findAllByFoundationId(foundationId);
            List<FoundationDetailsResponseDto.FoundationHasUsers> foundationHasUsersDto = foundationHasUsers.stream()
                    .map(FoundationMapper::toDto)
                    .collect(Collectors.toList());
            foundationDetail.setFoundationHasUsers(foundationHasUsersDto);
            return foundationDetail;
        }
        throw new ValidationException("Foundation is not found", ErrorCode.FOUNDATION_NOT_FOUND);
    }
}
