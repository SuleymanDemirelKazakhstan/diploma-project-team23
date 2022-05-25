package kz.sdu.project.sauapbackend.service;

import kz.sdu.project.sauapbackend.dto.request.AddNewMonthlyDonationRequestDto;
import kz.sdu.project.sauapbackend.dto.response.GetUserMonthDonationResponseDto;
import kz.sdu.project.sauapbackend.dto.response.MainCreateResponse;

import java.util.List;
import java.util.Optional;

public interface UserMonthlyDonateService {

    MainCreateResponse addNewMonthlyDonation(AddNewMonthlyDonationRequestDto request);

    Optional<List<GetUserMonthDonationResponseDto>> getUserDonationList(Long userId);

    void processMonthlyPayment();

}
