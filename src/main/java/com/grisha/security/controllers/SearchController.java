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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private UserService userService;
    @GetMapping("/search/{pageNum}")
    public String search(Model model, Principal principal, @RequestParam String search, @PathVariable("pageNum") int currentPage) {
            Page<Vacancy> page = userService.findSearchPage(currentPage, search);
            int totalPages = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<Vacancy> vacancies = page.getContent();
            model.addAttribute("search", search);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("vacancies", vacancies);
            return "search";
    }
}
