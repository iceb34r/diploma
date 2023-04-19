package com.grisha.security.repositories;

import com.grisha.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    public Set<Role> findRoleById(Long id);
}
