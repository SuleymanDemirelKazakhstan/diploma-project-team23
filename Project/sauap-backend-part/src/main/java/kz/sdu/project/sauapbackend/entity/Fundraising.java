package kz.sdu.project.sauapbackend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "fundraising")
public class Fundraising extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fundraise_title", length = 500, nullable = false)
    private String fundraiseTitle;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "goal_amount", nullable = false)
    private Double goalAmount;
    @Column(name = "short_title")
    private String shortTitle;
    @Column(length = 1000)
    private String description;
    @Column(name = "document_link")
    private String documentLink;
    @Column(name = "collected_amount")
    private Double collectedAmount;
    @Column(name = "is_done")
    private Boolean isDone;
    @Column(nullable = false)
    private Long contributors = 0L;
    @Column(name = "foundation_id", nullable = false)
    private Long foundationId;


}
