package com.grisha.security.repositories;

import com.grisha.security.entities.Vacancy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
    public Vacancy findVacancyById(Long id);
}
