package com.grisha.security.services;

import com.grisha.security.UserDetailsImpl;
import com.grisha.security.dto.ResumeDto;
import com.grisha.security.dto.UserDto;
import com.grisha.security.dto.VacancyDto;
import com.grisha.security.entities.*;
import com.grisha.security.exceptions.NotFoundException;
import com.grisha.security.repositories.*;
import com.grisha.security.utils.DtoUtils;
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
    private ResumeRepository resumeRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DtoUtils dtoUtils;

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
        return userFromDb.orElseThrow(() -> new NotFoundException(String.format("Can't find user with id [%d]", id)));
    }

    public User findUserByEmail(String email) {
        Optional<User> userFromDb = userRepository.findByEmail(email);
        return userFromDb.orElseThrow(() -> new NotFoundException(String.format("Can't find user with email : %s ", email)));
    }


    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void createEmployer(UserDto userDto) {
        employerRepository.save(dtoUtils.toEmployerFromDto(userDto));
    }

    public void createApplicant(UserDto userDto) {
        applicantRepository.save(dtoUtils.toApplicantFromDto(userDto));
    }

    public void createVacancy(VacancyDto vacancyDto, Employer employer) {
        vacancyRepository.save(dtoUtils.toVacancyFromDto(vacancyDto, employer));
    }

    public void createResume(ResumeDto resumeDto, Applicant applicant) {
        Iterable<Resume> allResume = resumeRepository.findAll();
        for(Resume currentResume : allResume) {
            if (currentResume.getApplicant() == applicant) {
                currentResume.setBirthDate(resumeDto.getBirthDate());
                currentResume.setCity(resumeDto.getCity());
                currentResume.setPhone(resumeDto.getPhone());
                currentResume.setPosition(resumeDto.getPosition());
                currentResume.setSalary(resumeDto.getSalary());
                currentResume.setWorkExperience(resumeDto.getWorkExperience());
                currentResume.setSkills(resumeDto.getSkills());
                currentResume.setEducation(resumeDto.getEducation());
                resumeRepository.save(currentResume);
            }
            else { resumeRepository.save(dtoUtils.toResumeFromDto(resumeDto, applicant)); }
        }
    }

    public void update(UserDto userDto) {
        dtoUtils.toUserFromDto(userDto);
    }

    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    public Employer getCurrentEmployer(User user) {
        Employer employer = employerRepository.findEmployerByUserId(user.getId());
        return employer;
    }
}
