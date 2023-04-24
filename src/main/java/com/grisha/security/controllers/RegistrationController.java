package com.grisha.security.controllers;

import com.grisha.security.dto.UserDto;
import com.grisha.security.entities.Employer;
import com.grisha.security.entities.Role;
import com.grisha.security.services.UserService;
import com.grisha.security.entities.User;
import com.grisha.security.validator.UserValidator;
import com.grisha.security.сonfigs.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;
    @GetMapping
    public String registration(Model model, Principal principal) {
        model.addAttribute("userForm", new UserDto());
        return "registration";
    }

    @PostMapping
    public String addUser(@ModelAttribute("userForm") UserDto userForm, Role role, BindingResult bindingResult, Model model) {
//        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) { return "registration"; }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароль не совпадает");
            return "registration";
        }
        if (userForm.getPassword().length() < 5) {
            model.addAttribute("shortPasswordError", "Пароль должен содержать не менее 5 символов");
            return "registration";
        }
        if (userForm.getRoleConfirm().equals("ROLE_EMPLOYER")) { userService.createEmployer(userForm); }
        else { userService.createApplicant(userForm); }

        return "redirect:/login";
    }

}
