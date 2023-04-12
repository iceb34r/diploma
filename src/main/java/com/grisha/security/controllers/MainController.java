package com.grisha.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String homePage() {
        return "home page";
    }
    @GetMapping("/authenticated")
    public String authenticatedUsers() {
        return "page for authenticated users";
    }
}
