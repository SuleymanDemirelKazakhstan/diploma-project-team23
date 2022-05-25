package kz.sdu.project.sauapbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserCardsResponseDto {
    private Long cardId;
    private String maskedCardNumber;
    private String type;
}
