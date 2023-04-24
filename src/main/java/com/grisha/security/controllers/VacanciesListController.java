package com.grisha.security.controllers;

import com.grisha.security.entities.Employer;
import com.grisha.security.entities.User;
import com.grisha.security.entities.Vacancy;
import com.grisha.security.repositories.EmployerRepository;
import com.grisha.security.repositories.VacancyRepository;
import com.grisha.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/vaclist")
public class VacanciesListController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private VacancyRepository vacancyRepository;
    @GetMapping
    public String getMainPage(Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        Employer employer = employerRepository.findEmployerByUserId(user.getId());
        Iterable<Vacancy> vacancies = vacancyRepository.findAllByEmployerId(employer.getId());
        model.addAttribute("vacancies", vacancies);
        return "vaclist";
    }
}
