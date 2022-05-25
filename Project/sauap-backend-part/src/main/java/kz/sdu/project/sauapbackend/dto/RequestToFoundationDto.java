package kz.sdu.project.sauapbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RequestToFoundationDto {
    private String requestId;
    @JsonProperty("name")
    private String personName;
    private String phoneNumber;
    private Long foundationId;
    private String description;
    private Long userId;
}
