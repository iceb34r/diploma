package com.grisha.security.controllers;

import com.grisha.security.entities.Vacancy;
import com.grisha.security.repositories.VacancyRepository;
import com.grisha.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    private UserService userService;
    @Autowired
    private VacancyRepository vacancyRepository;
    @GetMapping("/mainpage")
    public String getAllPages(Model model, Principal principal) {
        model.addAttribute("search", new String());
        return getMainPage(model, principal, 1);
    }
    @GetMapping("/mainpage/page/{pageNum}")
    public String getMainPage(Model model, Principal principal, @PathVariable("pageNum") int currentPage) {

        Page<Vacancy> page = userService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Vacancy> vacancies = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("vacancies", vacancies);

        return "mainpage";
    }
}
