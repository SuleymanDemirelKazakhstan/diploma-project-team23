package kz.sdu.project.sauapbackend.dto;

import lombok.Data;

@Data
public class UserCardDto {
    private Long cardId;
    private String cardHolderName;
    private String cardNumber;
    private String cardValidPeriod;
    private String cvv;
}
