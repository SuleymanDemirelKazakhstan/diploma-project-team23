package kz.sdu.project.sauapbackend.dto.request;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String email;
    private String previousPassword;
    private String newPassword;
    private String checkNewPassword;
}
