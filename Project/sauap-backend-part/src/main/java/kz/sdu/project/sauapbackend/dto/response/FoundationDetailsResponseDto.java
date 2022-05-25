package kz.sdu.project.sauapbackend.dto.response;

import kz.sdu.project.sauapbackend.dto.FoundationDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class FoundationDetailsResponseDto extends FoundationDto {

    private String documentLink;
    private String description;
    private String objective;
    private Double rating;
    private String bin;
    private String contactName;
    private String phoneNumber;
    private String websiteUrl;
    private String address;
    private List<FoundationHasUsers> foundationHasUsers;

    @Data
    public static class FoundationHasUsers {
        private String role;
        private Boolean isSubscribed;
        private Long userId;
    }

}
