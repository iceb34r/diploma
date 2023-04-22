package com.grisha.security.controllers;

import com.grisha.security.dto.VacancyDto;
import com.grisha.security.entities.Employer;
import com.grisha.security.entities.User;
import com.grisha.security.entities.Vacancy;
import com.grisha.security.repositories.EmployerRepository;
import com.grisha.security.repositories.VacancyRepository;
import com.grisha.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/vacancydetails/{id}")
public class VacancyDetailController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private VacancyRepository vacancyRepository;
    @GetMapping
    public String vacancyDetails(@PathVariable("id") Long id, Model model, Principal principal) {
        Vacancy vacancy = vacancyRepository.findVacancyById(id);
        Employer employer = vacancy.getEmployer();
        boolean isLoggedIn = userService.isLoggedIn();
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("employer", employer);
        return "vacancydetails";
    }
}
