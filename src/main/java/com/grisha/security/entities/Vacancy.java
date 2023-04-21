package com.grisha.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vacancys")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    @Column(name = "position")
    private String position;
    @Column(name = "salary")
    private Integer salary;
    @Column(name = "schedule")
    private String schedule;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employers_id")
    private Employer employer;
    @Column(name = "city")
    private String city;
    @Column(name = "creation_date")
    private LocalDate creationDate;
}
