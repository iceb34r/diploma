package com.grisha.security.services;

import com.grisha.security.UserDetailsImpl;
import com.grisha.security.dto.UserDto;
import com.grisha.security.entities.Applicant;
import com.grisha.security.entities.Employer;
import com.grisha.security.exceptions.NotFoundException;
import com.grisha.security.repositories.ApplicantRepository;
import com.grisha.security.repositories.EmployerRepository;
import com.grisha.security.repositories.RolesRepository;
import com.grisha.security.repositories.UserRepository;
import com.grisha.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmployerRepository employerRepository;
    @Autowired
    ApplicantRepository applicantRepository;
    @Autowired
    RolesRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("The username with email %s doesn't exist", email));
        }
        return new UserDetailsImpl(user);
    }
    public User findUserById(Long id) {
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElseThrow(()-> new NotFoundException(String.format("Can't find user with id [%d]",id)));
    }

    public User findUserByEmail(String email) {
        Optional<User> userFromDb = userRepository.findByEmail(email);
        return userFromDb.orElseThrow(()-> new NotFoundException(String.format("Can't find user with email : %s ", email)));
    }

    public List<User> allUsers() { return userRepository.findAll(); }

    public User toUserFromDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setRoles(roleRepository.findRoleByName(userDto.getRoleConfirm()));
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public Employer toEmployerFromDto(UserDto userDto) {
        Employer employer = new Employer();
        employer.setCompanyName(userDto.getCompanyName());
        employer.setUser(toUserFromDto(userDto));
        return employer;
    }

    public Applicant toApplicantFromDto(UserDto userDto) {
        Applicant applicant = new Applicant();
        applicant.setUser(toUserFromDto(userDto));
        return applicant;
    }

    public void createEmployer(UserDto userDto) {
        employerRepository.save(toEmployerFromDto(userDto));
    }

    public void createApplicant(UserDto userDto) {
        applicantRepository.save(toApplicantFromDto(userDto));
    }

    public void update(UserDto userDto) {
        toUserFromDto(userDto);
    }
    public void delete(String email) { userRepository.deleteByEmail(email); }

}
