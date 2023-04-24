package com.grisha.security.controllers;

import com.grisha.security.dto.ResumeDto;
import com.grisha.security.entities.Applicant;
import com.grisha.security.entities.Resume;
import com.grisha.security.entities.User;
import com.grisha.security.repositories.ApplicantRepository;
import com.grisha.security.repositories.ResumeRepository;
import com.grisha.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/myresume")
public class ResumeCreationController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private ResumeRepository resumeRepository;
    @GetMapping
    public String getResumePage(Model model, Principal principal, ResumeDto resumeDto) {
        User user = userService.findUserByEmail(principal.getName());
        Applicant applicant = applicantRepository.findApplicantByUserId(user.getId());
        Resume resume = resumeRepository.findResumeByApplicantId(applicant.getId());
        model.addAttribute("resumeInfo", resume);
        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("currentUser", user);
        return "myresume";
    }

    @PutMapping
    public String resumeCreator(@ModelAttribute("resumeDto") ResumeDto resumeDto, Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        Applicant applicant = applicantRepository.findApplicantByUserId(user.getId());
        userService.createResume(resumeDto, applicant);
        return "redirect:/myresume";
    }

}
