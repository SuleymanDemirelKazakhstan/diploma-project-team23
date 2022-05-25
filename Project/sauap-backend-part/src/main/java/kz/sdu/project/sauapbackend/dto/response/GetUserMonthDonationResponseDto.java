package kz.sdu.project.sauapbackend.dto.response;

import kz.sdu.project.sauapbackend.dto.FoundationDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserMonthDonationResponseDto {
    private Long id;
    private Double amount;
    private GetUserCardsResponseDto cardInfo;
    private FoundationDto foundation;
}
