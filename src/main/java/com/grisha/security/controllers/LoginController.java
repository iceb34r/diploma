package com.grisha.security.controllers;

import com.grisha.security.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String login(Model model, String error, String logout) {
        if (logout != null) {
            model.addAttribute("messageLogout", "Вы вышли с аккаунта");
        }
        return "login";
    }
}
