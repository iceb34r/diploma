package com.grisha.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {
    @GetMapping("/mainpage")
    public String getMainPage(Model model) {
        return "mainpage";
    }
}
