package com.grisha.security.controllers;

import com.grisha.security.entities.Role;
import com.grisha.security.services.UserService;
import com.grisha.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping
    public String addUser(@ModelAttribute("userForm") User userForm, Role role, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) { return "registration"; }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Password didn't match");
            return "registration";
        }
        userService.create(userForm);
        return "redirect:/test";
    }
}
