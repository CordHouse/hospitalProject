package com.example.hospitalproject.Service.Email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(MimeMessage email) {
        javaMailSender.send(email);
    }
}
