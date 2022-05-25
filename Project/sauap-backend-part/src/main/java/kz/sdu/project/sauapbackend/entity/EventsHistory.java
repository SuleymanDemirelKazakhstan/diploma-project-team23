package kz.sdu.project.sauapbackend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "events_history")
public class EventsHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_subscription_id")
    private Long eventSubscriptionId;
    @Column(name = "user_role", length = 100)
    private String userRole;
    @Column(name = "user_rate")
    private Double userRate;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "event_id")
    private Long eventId;

}
