package com.grisha.security.utils;

import com.grisha.security.dto.ResumeDto;
import com.grisha.security.dto.UserDto;
import com.grisha.security.dto.VacancyDto;
import com.grisha.security.entities.*;
import com.grisha.security.repositories.RolesRepository;
import com.grisha.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DtoUtils {
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
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
        vacancy.setEmployer(employer);
        vacancy.setCity(vacancyDto.getCity());
        vacancy.setCreationDate(LocalDate.now());
        vacancy.setResponsibilities(vacancyDto.getResponsibilities());
        vacancy.setConditions(vacancyDto.getConditions());
        vacancy.setRequirements(vacancyDto.getRequirements());
        return vacancy;
    }

    public Resume toResumeFromDto(ResumeDto resumeDto, Applicant applicant) {
        Resume resume = new Resume();
        resume.setApplicant(applicant);
        resume.setBirthDate(resumeDto.getBirthDate());
        resume.setCity(resumeDto.getCity());
        resume.setPhone(resumeDto.getPhone());
        resume.setPosition(resumeDto.getPosition());
        resume.setSalary(resumeDto.getSalary());
        resume.setWorkExperience(resumeDto.getWorkExperience());
        resume.setSkills(resumeDto.getSkills());
        resume.setEducation(resumeDto.getEducation());
        return resume;
    }
}
