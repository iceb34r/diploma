package com.grisha.security.repositories;

import com.grisha.security.entities.Applicant;
import com.grisha.security.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends CrudRepository<Applicant, Long> {
    public Applicant findApplicantByUserId(Long id);
}
