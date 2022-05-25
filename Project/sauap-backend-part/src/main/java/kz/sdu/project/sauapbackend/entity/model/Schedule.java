package kz.sdu.project.sauapbackend.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule implements Serializable {
    private String start;
    private String end;
}
