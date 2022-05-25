package kz.sdu.project.sauapbackend.dto.request;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private Long foundationId;
    private Long userId;
    private Long cardId;
    private Double amount;
    private Long fundraiseId;
}
