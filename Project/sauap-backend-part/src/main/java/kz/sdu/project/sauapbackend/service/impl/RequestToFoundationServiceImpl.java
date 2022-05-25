package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.dto.RequestToFoundationDto;
import kz.sdu.project.sauapbackend.dto.response.ServerDefaultResponse;
import kz.sdu.project.sauapbackend.entity.Foundation;
import kz.sdu.project.sauapbackend.entity.RequestToFoundation;
import kz.sdu.project.sauapbackend.entity.Users;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.mapper.RequestToFoundationMapper;
import kz.sdu.project.sauapbackend.repository.FoundationRepository;
import kz.sdu.project.sauapbackend.repository.RequestToFoundationRepository;
import kz.sdu.project.sauapbackend.repository.UsersRepository;
import kz.sdu.project.sauapbackend.service.RequestsToFoundationService;
import kz.sdu.project.sauapbackend.utils.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestToFoundationServiceImpl implements RequestsToFoundationService {

    private final RequestToFoundationRepository requestToFoundationRepository;
    private final UsersRepository usersRepository;
    private final FoundationRepository foundationRepository;

    public RequestToFoundationServiceImpl(RequestToFoundationRepository requestToFoundationRepository, UsersRepository usersRepository, FoundationRepository foundationRepository) {
        this.requestToFoundationRepository = requestToFoundationRepository;
        this.usersRepository = usersRepository;
        this.foundationRepository = foundationRepository;
    }

    @Override
    public ServerDefaultResponse newRequest(RequestToFoundationDto request) {
        Optional<Users> usersOptional = usersRepository.findById(request.getUserId());
        if (usersOptional.isEmpty()) {
            throw new ValidationException(String.format("User not found by this id: %d", request.getUserId()), ErrorCode.USER_NOT_FOUND);
        }
        Optional<Foundation> foundationOptional = foundationRepository.findById(request.getFoundationId());
        if (foundationOptional.isEmpty()) {
            throw new ValidationException(String.format("Foundation not found by this id: %d", request.getFoundationId()), ErrorCode.FOUNDATION_NOT_FOUND);
        }

        RequestToFoundation requestToFoundation = RequestToFoundationMapper.toEntity(request);
        requestToFoundation.setRequestId(IdGenerator.generateUniqueId());
        requestToFoundation.setIsApproved(false);
        requestToFoundationRepository.saveAndFlush(requestToFoundation);

        ServerDefaultResponse response = new ServerDefaultResponse();
        response.setIsDone(true);
        response.setMessage(requestToFoundation.getRequestId());
        response.setProcessTime(LocalDateTime.now());
        return response;
    }

    @Override
    public List<RequestToFoundationDto> getRequestsByFoundationId(Long foundationId) {
        Optional<Foundation> foundationOptional = foundationRepository.findById(foundationId);
        if (foundationOptional.isEmpty()) {
            throw new ValidationException(String.format("Foundation not found by this id: %d", foundationId), ErrorCode.FOUNDATION_NOT_FOUND);
        }

        return requestToFoundationRepository.findAllByFoundationId(foundationId)
                .stream()
                .map(RequestToFoundationMapper::toDto)
                .collect(Collectors.toList());
    }
}
