package com.grisha.security.repositories;

import entities.Vacancy;
import org.springframework.data.repository.CrudRepository;

public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
}
