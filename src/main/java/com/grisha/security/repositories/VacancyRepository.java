package com.grisha.security.repositories;

import com.grisha.security.entities.Employer;
import com.grisha.security.entities.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    public Vacancy findVacancyById(Long id);
    public Iterable<Vacancy> findAllByEmployerId(Long id);
    public Page<Vacancy> findAllByEmployerId(Long id, Pageable pageable);
    public Page<Vacancy> findVacanciesByPositionContains(String position, Pageable pageable);
    public Page<Vacancy> findVacanciesByPositionContainsAndEmployer(String position, Pageable pageable, Employer employer);
}
