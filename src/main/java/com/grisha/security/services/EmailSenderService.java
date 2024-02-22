package com.grisha.security.services;

import com.grisha.security.dto.UserDto;
import com.grisha.security.entities.*;
import com.grisha.security.repositories.ApplicantRepository;
import com.grisha.security.repositories.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;

import javax.mail.*;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.smtp.SMTPSSLTransport;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSenderService {

    JavaMailSender mailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private ResumeRepository resumeRepository;

    public void sendEmail(String toEmail, User user, Vacancy vacancy) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        Session session = Session.getInstance(props);

        Transport transport = new SMTPSSLTransport(session, new URLName("smtp.mail.ru"));
        transport.connect("smtp.mail.ru", "","");

        Applicant applicant = applicantRepository.findApplicantByUserId(user.getId());
        Resume resume = resumeRepository.findResumeByApplicantId(applicant.getId());
        Employer employer = vacancy.getEmployer();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("anim24@list.ru"));

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject("Отклик на вакансию " + vacancy.getPosition());
        message.setText("Отклик на вакансию " + vacancy.getPosition() + "\nРезюме:" +
                "\nФИО: " + user.getLastname() + " " + user.getName() + " " + user.getSurname() +
                "\nГород проживания: " + resume.getCity() + "\nЖелаемая позиция: " + resume.getPosition() +
                "\nПредпочитаемая зарплата: " + resume.getSalary() + " ₽" +
                "\nДата рождения: " + resume.getBirthDate() + "\nОпыт работы: " + resume.getWorkExperience() +
                "\nОбразование: " + resume.getEducation() + "\nКлючевые навыки: " + resume.getSkills() +
                "\nКонтакты для связи: Email: " + user.getEmail() + " Телефон: " + resume.getPhone());

        transport.sendMessage(message, message.getAllRecipients());
    }

    public void sendRecommendation(String toEmail, User user, Vacancy vacancy, UserDto userDto) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        Session session = Session.getInstance(props);

        Transport transport = new SMTPSSLTransport(session, new URLName("smtp.mail.ru"));
        transport.connect("smtp.mail.ru", "anim24@list.ru","X8whdMBj2UKTftSPBNpn");

        Applicant applicant = applicantRepository.findApplicantByUserId(user.getId());
        Resume resume = resumeRepository.findResumeByApplicantId(applicant.getId());
        Employer employer = vacancy.getEmployer();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("anim24@list.ru"));

        message.setRecipient(Message.RecipientType.TO, new InternetAddress("anim24@list.ru"));
        message.setSubject("Рекомендация на вакансию " + vacancy.getPosition());
        message.setText("Рекомендация на вакансию " + vacancy.getPosition() + "\nКонтактные данные:"
                        + "\nФИО: " + userDto.getName() + "\nЭлектронная почта: " + userDto.getEmail());

        transport.sendMessage(message, message.getAllRecipients());
    }
}
