package com.grisha.security.controllers;

import com.grisha.security.dto.UserDto;
import com.grisha.security.dto.VacancyDto;
import com.grisha.security.entities.Employer;
import com.grisha.security.entities.User;
import com.grisha.security.entities.Vacancy;
import com.grisha.security.repositories.EmployerRepository;
import com.grisha.security.repositories.VacancyRepository;
import com.grisha.security.services.EmailSenderService;
import com.grisha.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;

@Controller
@RequestMapping(("/vacancydetails/{id}"))
public class VacancyDetailController {
    @Autowired
    private UserService userService;
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @GetMapping
    public String vacancyDetails(@PathVariable("id") Long id, Model model, Principal principal) throws MessagingException {
        Vacancy vacancy = vacancyRepository.findVacancyById(id);
        Employer employer = vacancy.getEmployer();
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("employer", employer);
        model.addAttribute("userDto", new UserDto());
        return "vacancydetails";
    }
    @PostMapping(params = "action=response")
    public String sendEmail(@PathVariable("id") Long id, Model model, Principal principal) throws MessagingException {
        Vacancy vacancy = vacancyRepository.findVacancyById(id);
        User user = userService.findUserByEmail(principal.getName());
        Employer employer = vacancy.getEmployer();
        String employerEmail = employer.getUser().getEmail();
        emailSenderService.sendEmail(employerEmail, user, vacancy);
        return "emailsend";
    }

    @PostMapping(params = "action=recommend")
    public String sendRecommend(@PathVariable("id") Long id, @ModelAttribute("userDto") UserDto userDto, Model model, Principal principal) throws MessagingException {
        Vacancy vacancy = vacancyRepository.findVacancyById(id);
        User user = userService.findUserByEmail(principal.getName());
        Employer employer = vacancy.getEmployer();
        String employerEmail = employer.getUser().getEmail();
        emailSenderService.sendRecommendation(employerEmail, user, vacancy, userDto);
        return "emailsend";
    }



//    @PostMapping("/rec")
//    public String sendRec(@PathVariable("id") Long id, Model model, Principal principal) throws MessagingException {
//        Vacancy vacancy = vacancyRepository.findVacancyById(id);
//        User user = userService.findUserByEmail(principal.getName());
//        Employer employer = vacancy.getEmployer();
//        String employerEmail = employer.getUser().getEmail();
//        emailSenderService.sendEmail(employerEmail, user, vacancy);
//        return "emailsend";
//    }
}
