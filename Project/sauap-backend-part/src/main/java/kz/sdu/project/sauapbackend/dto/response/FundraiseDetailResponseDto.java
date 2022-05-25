package kz.sdu.project.sauapbackend.dto.response;

import kz.sdu.project.sauapbackend.dto.FundraiseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FundraiseDetailResponseDto extends FundraiseDto {
    private String shortTitle;
    private String description;
    private String documentLink;
}
