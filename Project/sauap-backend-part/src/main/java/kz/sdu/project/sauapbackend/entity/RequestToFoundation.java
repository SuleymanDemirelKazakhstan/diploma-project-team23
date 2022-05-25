package kz.sdu.project.sauapbackend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "requests_to_foundation")
public class RequestToFoundation extends BaseEntity {
    @Id
    @Column(name = "request_id", length = 100)
    private String requestId;
    @Column(name = "person_name", length = 150)
    private String personName;
    @Column(name = "phone_number", length = 100)
    private String phoneNumber;
    @Column(name = "userId", nullable = false)
    private Long userId;
    @Column(name = "foundation_id", nullable = false)
    private Long foundationId;
    @Column(length = 900)
    private String description;
    @Column(name = "is_approved")
    private Boolean isApproved;
}
