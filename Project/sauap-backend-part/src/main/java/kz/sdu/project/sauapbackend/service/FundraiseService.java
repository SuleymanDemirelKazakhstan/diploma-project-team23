package kz.sdu.project.sauapbackend.service;

import kz.sdu.project.sauapbackend.dto.FundraiseDto;
import kz.sdu.project.sauapbackend.dto.request.CreateFundraiseRequestDto;
import kz.sdu.project.sauapbackend.dto.request.PaymentRequestDto;
import kz.sdu.project.sauapbackend.dto.response.FundraiseDetailResponseDto;
import kz.sdu.project.sauapbackend.dto.response.MainCreateResponse;
import kz.sdu.project.sauapbackend.dto.response.ServerDefaultResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FundraiseService {

    FundraiseDetailResponseDto getFundraiseById(Long fundraiseId);

    List<FundraiseDto> getFundraiseByFoundationId(Long foundationId);

    List<FundraiseDto> getAllFundraises(boolean isCompleted);

    MainCreateResponse createFundraise(CreateFundraiseRequestDto createFundraiseRequestDto,
                                       MultipartFile documentFile,
                                       MultipartFile imageFile,
                                       Long foundationId);

    ServerDefaultResponse donateToFundraise(PaymentRequestDto paymentRequestDto);

}
