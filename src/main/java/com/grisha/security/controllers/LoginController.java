package com.grisha.security.controllers;

import com.grisha.security.dto.UserDto;
import com.grisha.security.entities.User;
import com.grisha.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String login(Model model, String error, String logout) {
        if (logout != null) {
            model.addAttribute("messageLogout", "Вы вышли с аккаунта");
        }
        return "login";
    }
}
