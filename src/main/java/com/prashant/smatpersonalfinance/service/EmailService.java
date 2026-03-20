package com.prashant.smatpersonalfinance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${app.mail.from}")
    private String from1;
    private final JavaMailSender javaMailSender;
    public void sendEmail(String to,String subject,String body ){
        try {
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            System.out.println(from1);
            simpleMailMessage.setFrom(from1);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);

        }
        catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }
}
