package com.grisha.security.controllers;

import com.grisha.security.entities.Employer;
import com.grisha.security.entities.Role;
import com.grisha.security.services.UserService;
import com.grisha.security.entities.User;
import com.grisha.security.validator.UserValidator;
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
    @Autowired
    private UserValidator userValidator;

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping
    public String addUser(@ModelAttribute("userForm") User userForm, Employer employerForm, Role role, BindingResult bindingResult, Model model) {
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
        if(userForm.getRoleConfirm().equals("EMPLOYER")) {
            userService.create(userForm);
            return "redirect:/registrationEmp";
        }
        userService.create(userForm);
        return "redirect:/test";
    }

    @PostMapping("/registrationEmp")
    public String addEmployer(@ModelAttribute("employerForm") Employer employerForm, BindingResult bindingResult, Model model) {

    }
}
