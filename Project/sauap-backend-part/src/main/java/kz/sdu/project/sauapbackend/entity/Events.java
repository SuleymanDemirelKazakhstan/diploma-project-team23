package kz.sdu.project.sauapbackend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "events")
public class Events extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;
    @Column(name = "event_name", length = 50)
    private String eventName;
    @Column(name = "limits_of_people")
    private Integer limit;
    private String description;
    @Column(name = "is_approved")
    private Boolean isApproved;
    private String purpose;
    @Column(length = 100)
    private String location;
    @Column(name = "final_rating")
    private Double finalRating;
    @Column(name = "event_date")
    private LocalDateTime eventDate;

}

