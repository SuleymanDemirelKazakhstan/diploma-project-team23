package kz.sdu.project.sauapbackend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "foundation_has_users")
public class FoundationHasUsers extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "foundation_id")
    private Long foundationId;
    private Double rated;
    @Column(name = "is_subscribed")
    private Boolean isSubscribed;
    @Column(length = 20)
    private String role;
}
