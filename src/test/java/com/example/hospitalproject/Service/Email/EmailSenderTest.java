package com.example.hospitalproject.Service.Email;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
public class EmailSenderTest {
    @InjectMocks
    private EmailSenderService emailSenderService;

    @Mock
    private JavaMailSender javaMailSender;

    @DisplayName("이메일 전송 테스트")
    @Test
    public void sendEmailTest() {
        // given
        MimeMessage email = javaMailSender.createMimeMessage();

        // when
        emailSenderService.sendEmail(email);

        // then
    }
}
