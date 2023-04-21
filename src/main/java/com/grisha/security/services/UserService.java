package com.grisha.security.services;

import com.grisha.security.UserDetailsImpl;
import com.grisha.security.dto.UserDto;
import com.grisha.security.dto.VacancyDto;
import com.grisha.security.entities.Applicant;
import com.grisha.security.entities.Employer;
import com.grisha.security.entities.Vacancy;
import com.grisha.security.exceptions.NotFoundException;
import com.grisha.security.repositories.*;
import com.grisha.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private VacancyRepository vacancyRepository;
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

    public void createEmployer(UserDto userDto) {
        employerRepository.save(toEmployerFromDto(userDto));
    }

    public void createApplicant(UserDto userDto) {
        applicantRepository.save(toApplicantFromDto(userDto));
    }
    public void createVacancy(VacancyDto vacancyDto, Employer employer) { vacancyRepository.save(toVacancyFromDto(vacancyDto, employer)); }

    public void update(UserDto userDto) {
        toUserFromDto(userDto);
    }
    public void delete(String email) { userRepository.deleteByEmail(email); }

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

    public Vacancy toVacancyFromDto(VacancyDto vacancyDto, Employer employer) {
        Vacancy vacancy = new Vacancy();
        vacancy.setPosition(vacancyDto.getPosition());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setSchedule(vacancyDto.getSchedule());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setEmployer(employer);
        vacancy.setCity(vacancyDto.getCity());
        vacancy.setCreationDate(LocalDate.now());
        return vacancy;
    }
    public Employer getCurrentEmployer(User user) {
        Employer employer = employerRepository.findEmployerByUserId(user.getId());
        return employer;
    }
}
