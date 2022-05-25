package kz.sdu.project.sauapbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String iin;
    private String city;
    private String address;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime birthDate;
    private String phoneNumber;
    private String gender;
    private Boolean isActive;
}
