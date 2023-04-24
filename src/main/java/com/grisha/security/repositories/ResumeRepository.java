package com.grisha.security.repositories;

import com.grisha.security.entities.Resume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends CrudRepository<Resume, Long> {
    public Resume findResumeByApplicantId(Long id);

}
