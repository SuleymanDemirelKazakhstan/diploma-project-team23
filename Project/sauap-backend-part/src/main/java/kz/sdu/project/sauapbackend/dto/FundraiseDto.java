package kz.sdu.project.sauapbackend.dto;

import lombok.Data;

@Data
public class FundraiseDto {
    private Long id;
    private String fundraiseTitle;
    private String photoUrl;
    private Double goalAmount;
    private Double collectedAmount;
    private Integer processPercent;
    private Integer contributors = 0;
    private Long foundationId;
}
