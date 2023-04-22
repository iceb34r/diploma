package com.grisha.security.controllers;

import com.grisha.security.dto.VacancyDto;
import com.grisha.security.entities.Employer;
import com.grisha.security.entities.User;
import com.grisha.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/vacancycreation")
public class VacancyCreationController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String getForm(Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        boolean isLoggedIn = userService.isLoggedIn();
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("vacancyDto", new VacancyDto());
        return "/vacancycreation";
    }

    @PostMapping
    public String createVacancy(@ModelAttribute("vacancyDto") VacancyDto vacancyDto, BindingResult bindingResult, Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        Employer employer = userService.getCurrentEmployer(user);
        userService.createVacancy(vacancyDto, employer);
        return "redirect:/mainpage";
    }
}
