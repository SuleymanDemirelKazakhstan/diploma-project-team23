package kz.sdu.project.sauapbackend.dto.request;

import lombok.Data;

@Data
public class CreateFoundationDto {
    private String city;
    private String foundationName;
    private String bin;
    private String contactName;
    private String phoneNumber;
    private FoundationCredits credits;

    @Data
    public static class FoundationCredits {
        private String bin;
        private String kbe;
        private String knp;
        private String bic;
        private String account;
        private String legalAddress;
    }
}
