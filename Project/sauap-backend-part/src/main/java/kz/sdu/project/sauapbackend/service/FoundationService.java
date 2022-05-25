package kz.sdu.project.sauapbackend.service;

import kz.sdu.project.sauapbackend.dto.FoundationDto;
import kz.sdu.project.sauapbackend.dto.request.CreateFoundationDto;
import kz.sdu.project.sauapbackend.dto.request.UpdateFoundationRequestDto;
import kz.sdu.project.sauapbackend.dto.response.FoundationDetailsResponseDto;
import kz.sdu.project.sauapbackend.dto.response.MainCreateResponse;
import kz.sdu.project.sauapbackend.dto.response.UpdateImageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FoundationService {

    Optional<List<FoundationDto>> getAllFoundations();

    FoundationDetailsResponseDto getUserFoundations(Long userId);

    FoundationDetailsResponseDto getFoundationById(Long foundationId);

    MainCreateResponse requestToCreateNewFoundation(CreateFoundationDto dto, MultipartFile documentFile, Long userId);

    UpdateImageResponseDto updateFoundationImage(Long foundationId, MultipartFile imageFile);

    void update(UpdateFoundationRequestDto foundationRequestDto, Long foundationId);

}
