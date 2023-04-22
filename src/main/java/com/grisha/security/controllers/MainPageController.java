package com.grisha.security.controllers;

import com.grisha.security.entities.User;
import com.grisha.security.entities.Vacancy;
import com.grisha.security.repositories.VacancyRepository;
import com.grisha.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.CurrencyStyleFormatter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/mainpage")
public class MainPageController {

    @Autowired
    private UserService userService;
    @Autowired
    private VacancyRepository vacancyRepository;
    @GetMapping
    public String getMainPage(Model model, Principal principal) {
        Iterable<Vacancy> vacancies = vacancyRepository.findAll();
        boolean isLoggedIn = userService.isLoggedIn();
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("vacancies", vacancies);
        return "mainpage";
    }

}
