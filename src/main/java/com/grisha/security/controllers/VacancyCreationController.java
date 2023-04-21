package com.grisha.security.controllers;

import com.grisha.security.dto.VacancyDto;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vacancycreation")
public class VacancyCreationController {
    @GetMapping
    public String getForm(Model model) {
        model.addAttribute("vacancyDto", new VacancyDto());
        return "/vacancycreation";
    }

    @PostMapping("/vacancycreation")
    public String createVacancy(@ModelAttribute("vacancyDto") VacancyDto vacancyDto, BindingResult bindingResult, Model model) {

        return "redirect:/mainpage";
    }
}
