package com.grisha.security.repositories;

import entities.Resume;
import org.springframework.data.repository.CrudRepository;

public interface ResumeRepository extends CrudRepository<Resume, Long> {
}
