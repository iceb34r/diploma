package com.grisha.security.dto;

import com.grisha.security.entities.Applicant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto {
    protected Long id;
    private String name;
    private String lastname;
    private String surname;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String city;
    private String phone;
    private String position;
    private Integer salary;
    private String workExperience;
    private String skills;
    private String education;
}
