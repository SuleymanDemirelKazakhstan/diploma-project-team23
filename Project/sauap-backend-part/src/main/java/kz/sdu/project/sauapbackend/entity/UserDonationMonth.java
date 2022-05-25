package kz.sdu.project.sauapbackend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "user_donation_month")
public class UserDonationMonth extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_donation_month_id")
    private Long userDonationId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "foundation_id", nullable = false)
    private Long foundationId;
    @Column(name = "card_id", nullable = false)
    private Long cardId;
    @Column(nullable = false)
    private Double amount;
    @Column(name = "donate_day", nullable = false)
    private Integer donateDay = 1;
    @Column(name = "is_done", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private Boolean isDone;
}
