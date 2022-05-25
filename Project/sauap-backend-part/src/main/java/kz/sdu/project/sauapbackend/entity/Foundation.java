package kz.sdu.project.sauapbackend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "foundations")
public class Foundation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foundation_id")
    private Long foundationId;
    @Column(length = 50, nullable = false)
    private String city;
    @Column(name = "foundation_name", length = 100, nullable = false)
    private String foundationName;
    @Column(length = 20, nullable = false)
    private String bin;
    private String address;
    private String websiteUrl;
    @Column(name = "contact_name", length = 100, nullable = false)
    private String contactName;
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;
    @Column(name = "photo_link")
    private String photoLink;
    @Column(name = "document_link", nullable = false)
    private String documentLink;
    private String description;
    @Column(length = 100)
    private String objective;
    @Column(length = 50)
    private String type;
    private Double rating;
    @Column(name = "is_approved")
    private Boolean isApproved;


}
