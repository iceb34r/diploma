package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vacancys")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "position")
    private String position;
    @Column(name = "salary")
    private Integer salary;
    @Column(name = "schedule")
    private String schedule;
    @Column(name = "description")
    private String description;
}
