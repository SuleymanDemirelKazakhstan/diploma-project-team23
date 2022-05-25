package kz.sdu.project.sauapbackend.dto.request;

import lombok.Data;

@Data
public class AddNewMonthlyDonationRequestDto {
    private Long foundationId;
    private Long userId;
    private Long cardId;
    private Double amount;
}
