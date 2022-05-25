package kz.sdu.project.sauapbackend.dto.response;

import kz.sdu.project.sauapbackend.dto.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfoDto extends UserDto {
    private BigDecimal donatedAmounts;
    private String photoLink;
}
