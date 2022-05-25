package kz.sdu.project.sauapbackend.dto.request;

import lombok.Data;

@Data
public class CreateFundraiseRequestDto {
    private String fundraiserTitle;
    private Double goalAmount;
    private String shortTitle;
    private String description;
}
