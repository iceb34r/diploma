package com.grisha.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grisha.security.entities.*;
import com.grisha.security.repositories.EmployerRepository;
import com.grisha.security.repositories.RolesRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    protected Long id;
    private String name;
    private String lastname;
    private String surname;
    private String email;
    private String password;
    private String passwordConfirm;
    private String roleConfirm;
    private Set<Role> roles;
    private String companyName;

}
