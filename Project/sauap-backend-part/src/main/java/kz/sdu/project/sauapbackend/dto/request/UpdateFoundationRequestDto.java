package kz.sdu.project.sauapbackend.dto.request;

import lombok.Data;

@Data
public class UpdateFoundationRequestDto {
    private String name;
    private String description;
    private String phone;
    private String address;
    private String website;
}
