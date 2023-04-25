package com.grisha.security.controllers;

import com.grisha.security.entities.Employer;
import com.grisha.security.entities.User;
import com.grisha.security.entities.Vacancy;
import com.grisha.security.repositories.EmployerRepository;
import com.grisha.security.repositories.VacancyRepository;
import com.grisha.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class VacanciesListController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private VacancyRepository vacancyRepository;
    @GetMapping("/vaclist/page/{pageNum}")
    public String getMainPage(Model model, Principal principal, @PathVariable("pageNum") int currentPage) {
        User user = userService.findUserByEmail(principal.getName());
        Employer employer = employerRepository.findEmployerByUserId(user.getId());
//        Iterable<Vacancy> vacancies = vacancyRepository.findAllByEmployerId(employer.getId());
//        model.addAttribute("vacancies", vacancies);

        Page<Vacancy> page = userService.findPageEmpVacs(currentPage, employer);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Vacancy> vacancies = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("vacancies", vacancies);
        return "vaclist";
    }
}
