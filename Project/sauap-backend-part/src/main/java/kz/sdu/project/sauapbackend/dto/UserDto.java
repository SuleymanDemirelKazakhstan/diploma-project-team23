package kz.sdu.project.sauapbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String iin;
    private String city;
    private String address;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime birthDate;
    private String phoneNumber;
    private String gender;
    private Boolean isActive;

}
