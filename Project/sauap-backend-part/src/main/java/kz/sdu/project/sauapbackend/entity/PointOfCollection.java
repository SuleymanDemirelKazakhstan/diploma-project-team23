package kz.sdu.project.sauapbackend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "donate_goods")
public class PointOfCollection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collectionId")
    private Long collectionId;
    @Column(nullable = false, length = 150)
    private String city;
    @Column(nullable = false, length = 50)
    private String type; // foods or clothes
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false)
    private String building;
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;
    @Column(name = "is_active")
    private Boolean isActive = false;
    @Column(name = "responsible_person", length = 50, nullable = false)
    private String responsiblePerson;
    @Column(nullable = false, length = 500)
    private String conditions;

}
