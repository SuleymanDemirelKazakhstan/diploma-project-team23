package kz.sdu.project.sauapbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "foundation_credits")
public class FoundationCredits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String bin;
    @Column(length = 30)
    private String kbe;
    @Column(length = 30)
    private String knp;
    @Column(length = 30)
    private String bic;
    @Column(length = 30)
    private String account;
    @Column(name = "legal_address", length = 150)
    private String legalAddress;
    @Column(name = "foundation_id")
    private Long foundationId;
}
