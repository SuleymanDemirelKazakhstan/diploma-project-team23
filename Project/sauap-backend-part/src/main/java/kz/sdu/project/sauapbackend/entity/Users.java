package kz.sdu.project.sauapbackend.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "users")
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(nullable = false)
    private String password;
    @Column(name = "first_name", length = 100)
    private String firstName;
    @Column(name = "last_name", length = 100)
    private String lastname;
    @Column(length = 100, nullable = false)
    private String email;
    @Column(length = 20)
    private String iin;
    @Column(length = 50)
    private String city;
    @Column(length = 100)
    private String address;
    @Column(name = "photo_link")
    private String photoLink;
    @Column(name = "birth_date")
    private LocalDateTime birthDate;
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    @Column(length = 10)
    private String gender;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "donated_amounts")
    private BigDecimal donatedAmounts = new BigDecimal(0);

}
