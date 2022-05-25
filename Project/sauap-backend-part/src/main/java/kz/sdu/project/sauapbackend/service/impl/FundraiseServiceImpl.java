package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.dto.FundraiseDto;
import kz.sdu.project.sauapbackend.dto.PaymentResultDto;
import kz.sdu.project.sauapbackend.dto.request.CreateFundraiseRequestDto;
import kz.sdu.project.sauapbackend.dto.request.PaymentRequestDto;
import kz.sdu.project.sauapbackend.dto.response.FundraiseDetailResponseDto;
import kz.sdu.project.sauapbackend.dto.response.MainCreateResponse;
import kz.sdu.project.sauapbackend.dto.response.ServerDefaultResponse;
import kz.sdu.project.sauapbackend.entity.Foundation;
import kz.sdu.project.sauapbackend.entity.Fundraising;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.mapper.FundraiseMapper;
import kz.sdu.project.sauapbackend.repository.FoundationRepository;
import kz.sdu.project.sauapbackend.repository.FundraiseRepository;
import kz.sdu.project.sauapbackend.service.AmazonS3Service;
import kz.sdu.project.sauapbackend.service.FundraiseService;
import kz.sdu.project.sauapbackend.service.PaymentService;
import kz.sdu.project.sauapbackend.service.ValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FundraiseServiceImpl implements FundraiseService {

    private final FundraiseRepository fundraiseRepository;
    private final FoundationRepository foundationRepository;
    private final ValidatorService<CreateFundraiseRequestDto> validator;
    private final AmazonS3Service amazonS3Service;
    private final PaymentService<PaymentRequestDto> paymentService;

    private static final String FUNDRAISE_DOC_FOLDER = "/fundraise/docs";
    private static final String FUNDRAISE_IMAGE_FOLDER = "/fundraise/images";

    public FundraiseServiceImpl(FundraiseRepository fundraiseRepository,
                                FoundationRepository foundationRepository,
                                ValidatorService<CreateFundraiseRequestDto> validator,
                                @Qualifier("ProcessDocumentsServiceImpl") AmazonS3Service amazonS3Service,
                                PaymentService<PaymentRequestDto> paymentService) {
        this.fundraiseRepository = fundraiseRepository;
        this.foundationRepository = foundationRepository;
        this.validator = validator;
        this.amazonS3Service = amazonS3Service;
        this.paymentService = paymentService;
    }

    @Override
    public List<FundraiseDto> getAllFundraises(boolean isCompleted) {
        List<Fundraising> fundraisingList = fundraiseRepository.findAllByOrderByIdDesc();
        if (isCompleted) {
            return fundraisingList.stream()
                    .filter(Fundraising::getIsDone)
                    .map(object -> {
                        FundraiseDto fundraiseDto = FundraiseMapper.toDto(object);
                        fundraiseDto.setProcessPercent(calculateProcessPercent(fundraiseDto.getGoalAmount(), fundraiseDto.getCollectedAmount()));
                        return fundraiseDto;
                    }).collect(Collectors.toList());
        }
        return fundraisingList.stream()
                .filter(object -> !object.getIsDone())
                .map(object -> {
            FundraiseDto fundraiseDto = FundraiseMapper.toDto(object);
            fundraiseDto.setProcessPercent(calculateProcessPercent(fundraiseDto.getGoalAmount(), fundraiseDto.getCollectedAmount()));
            return fundraiseDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<FundraiseDto> getFundraiseByFoundationId(Long foundationId) {
        Optional<Foundation> checkFoundation = foundationRepository.findById(foundationId);
        if (checkFoundation.isEmpty()) {
            throw new ValidationException("Foundation is not found", ErrorCode.FOUNDATION_NOT_FOUND);
        }

        List<Fundraising> fundraisingList = fundraiseRepository.findAllByFoundationIdOrderByIdDesc(foundationId);
        return fundraisingList.stream()
                .filter(object -> !object.getIsDone())
                .map(object -> {
                    FundraiseDto fundraiseDto = FundraiseMapper.toDto(object);
                    fundraiseDto.setProcessPercent(calculateProcessPercent(fundraiseDto.getGoalAmount(), fundraiseDto.getCollectedAmount()));
                    return fundraiseDto;
                }).collect(Collectors.toList());
    }

    @Override
    public FundraiseDetailResponseDto getFundraiseById(Long fundraiseId) {
        Optional<Fundraising> fundraisingOptional = fundraiseRepository.findById(fundraiseId);
        if (fundraisingOptional.isPresent()) {
            Fundraising fundraising = fundraisingOptional.get();
            FundraiseDetailResponseDto fundraiseDetail = FundraiseMapper.toDetailDto(fundraising);
            fundraiseDetail.setProcessPercent(calculateProcessPercent(fundraising.getGoalAmount(), fundraising.getCollectedAmount()));

            return fundraiseDetail;
        }
        throw new ValidationException("Fundraise not found", ErrorCode.FUNDRAISE_NOT_FOUND  );
    }

    @Override
    public MainCreateResponse createFundraise(CreateFundraiseRequestDto createFundraiseRequestDto,
                                              MultipartFile documentFile,
                                              MultipartFile imageFile,
                                              Long foundationId) {
        validator.validate(createFundraiseRequestDto);

        Optional<Foundation> optionalFoundation = foundationRepository.findById(foundationId);
        if (optionalFoundation.isEmpty()) {
            throw new ValidationException("Foundation not found by this given 'id'", ErrorCode.FOUNDATION_NOT_FOUND);
        }

        // save fundraise documents to the cloud
        String imageUrl = amazonS3Service.uploadFile(imageFile, FUNDRAISE_IMAGE_FOLDER);
        String documentUrl = amazonS3Service.uploadFile(documentFile, FUNDRAISE_DOC_FOLDER);

        try {
            Fundraising newFundraising = new Fundraising();
            newFundraising.setFundraiseTitle(createFundraiseRequestDto.getFundraiserTitle());
            newFundraising.setPhotoUrl(imageUrl);
            newFundraising.setGoalAmount(createFundraiseRequestDto.getGoalAmount());
            newFundraising.setShortTitle(createFundraiseRequestDto.getShortTitle());
            newFundraising.setDescription(createFundraiseRequestDto.getDescription());
            newFundraising.setDocumentLink(documentUrl);
            newFundraising.setCollectedAmount((double) 0);
            newFundraising.setIsDone(false);
            newFundraising.setFoundationId(foundationId);

            Fundraising savedFundraising = fundraiseRepository.saveAndFlush(newFundraising);
            log.info("CREATE FUNDRAISE | New fundraise created | foundationId: {}, {}", foundationId, savedFundraising);
            return new MainCreateResponse(savedFundraising.getId());
        } catch (Exception ex) {
            amazonS3Service.deleteFile(imageFile.getOriginalFilename(), FUNDRAISE_IMAGE_FOLDER);
            amazonS3Service.deleteFile(documentFile.getOriginalFilename(), FUNDRAISE_DOC_FOLDER);
            throw ex;
        }
    }

    @Override
    @Transactional
    public ServerDefaultResponse donateToFundraise(PaymentRequestDto paymentRequestDto) {
        log.info("DIRECT PAYMENT | Donate to special fundraise has started  | {}", paymentRequestDto);
        ServerDefaultResponse response = new ServerDefaultResponse();
        LocalDateTime processTime = LocalDateTime.now();
        try {
            PaymentResultDto paymentAndMessage = paymentService.makePayment(paymentRequestDto);

            Fundraising currentFundraise = fundraiseRepository.findById(paymentRequestDto.getFundraiseId()).get();
            long fundraiserCount = currentFundraise.getContributors() + 1;
            double collectedAmount = currentFundraise.getCollectedAmount() + paymentRequestDto.getAmount();
            boolean isDone = collectedAmount >= currentFundraise.getGoalAmount();

            currentFundraise.setContributors(fundraiserCount);
            currentFundraise.setCollectedAmount(collectedAmount);
            currentFundraise.setIsDone(isDone);

            fundraiseRepository.saveAndFlush(currentFundraise);

            response.setMessage(null);
            response.setIsDone(true);
            response.setProcessTime(processTime);
            log.info("DIRECT PAYMENT | {} | {} successfully done", paymentRequestDto, paymentAndMessage.getPaymentId());
        } catch (Exception ex) {
            response.setIsDone(false);
            response.setProcessTime(processTime);
            response.setMessage(ex.getMessage());
            log.error("ERROR WHILE PROCESS DIRECT PAYMENT | {}", ex.getMessage());
        }
        return response;
    }

    private Integer calculateProcessPercent(Double goalAmount, Double collectedAmount) {
        double percent = (collectedAmount * 100) / goalAmount;
        return (int) percent;
    }
}
