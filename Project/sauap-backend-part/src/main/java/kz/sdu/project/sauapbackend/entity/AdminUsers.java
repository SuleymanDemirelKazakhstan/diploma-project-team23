package kz.sdu.project.sauapbackend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "admin_users")
public class AdminUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_user_id")
    private Long adminUserId;
    @Column(length = 50)
    private String username;
    private String password;
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    @Column(name = "is_active")
    private Boolean isActive = false;
    @Column(name = "created_time")
    private LocalDateTime createdTime = LocalDateTime.now();
}
