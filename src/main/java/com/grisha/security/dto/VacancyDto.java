package com.grisha.security.dto;

import com.grisha.security.entities.Employer;
import com.grisha.security.entities.User;
import com.grisha.security.entities.Vacancy;
import com.grisha.security.repositories.VacancyRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDto {

    protected Long id;
    private String position;
    private Integer salary;
    private String schedule;
    private String description;
    private String companyName;
    private String city;
    private LocalDate creationDate;
}
