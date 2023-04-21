package com.grisha.security.repositories;

import com.grisha.security.entities.Employer;
import com.grisha.security.entities.User;
import com.grisha.security.exceptions.NotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends CrudRepository<Employer, Long> {
    public Employer findEmployerByUserId(Long id);

}
