package com.chivapchichi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender email;

    @Autowired
    public MailService(JavaMailSender email) {
        this.email = email;
    }

    public void siteRegistration(String mailTo) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailTo);
        message.setSubject("Регистрация на ресурсе");
        message.setText("Спасибо за регистрацию на нашем ресурсе.");

        email.send(message);
    }

    public void tournamentRegistration(String mailTo, String tournament) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailTo);
        message.setSubject("Регистрация на турнир");
        message.setText("Вы были зарегестрированы на турнир: " + tournament);

        email.send(message);
    }

    public void tournamentUnregistration(String mailTo, String tournament) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailTo);
        message.setSubject("Отмена регистрация на турнир");
        message.setText("Вами была отменена регистрация на турнир: " + tournament);

        email.send(message);
    }

    public void tournamentPayment(String mailTo, String tournament) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailTo);
        message.setSubject("Подтверждение регистрации на турнир");
        message.setText("Вами был оплачен взнос за участие в турнире: " + tournament);

        email.send(message);
    }

    public void tournamentCancelPayment(String mailTo, String tournament) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailTo);
        message.setSubject("Отмена оплаты");
        message.setText("Вам были возвращены средства за оплату участия в турнире: " + tournament);

        email.send(message);
    }
}
