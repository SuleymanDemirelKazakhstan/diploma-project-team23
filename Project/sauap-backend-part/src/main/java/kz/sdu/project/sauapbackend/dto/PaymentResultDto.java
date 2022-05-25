package kz.sdu.project.sauapbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResultDto {
    private String paymentId;
    private MailMessage message;
}
