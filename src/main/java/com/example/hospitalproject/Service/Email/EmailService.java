package com.example.hospitalproject.Service.Email;

import com.example.hospitalproject.Entity.Email.EmailToken;
import com.example.hospitalproject.Exception.Email.NotFoundEmailTokenInfoException;
import com.example.hospitalproject.Repository.Email.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailRepository emailRepository;
    private final EmailSenderService emailSenderService;
    private final JavaMailSender javaMailSender;

    /**
     * 전달된 이메일 링크 클릭 후 토큰 검증
     */
    @Transactional
    public EmailToken confirmEmail(String token) {
        EmailToken emailToken = getEmailToken(token);
        emailToken.setExpired("true");
        return emailToken;
    }

    /**
     * 토큰 정보를 담은 이메일 생성
     */
    @Transactional
    public String createEmailToken(String username, String email) {
        EmailToken emailToken = new EmailToken(username);
        emailRepository.save(emailToken);

        String toEmail = "http://localhost:8080/confirm/email?token="+emailToken.getId();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.addRecipients(Message.RecipientType.TO, email); // 받는 사람 이메일
            mimeMessage.setSubject("FnDoc 회원가입 이메일 인증"); // 메일 제목
            mimeMessage.setContent("<a href="+toEmail+">"+"인증 링크</a>", "text/html;charset=UTF-8"); // 내용
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(email);
//        simpleMailMessage.setSubject("회원가입 이메일 인증");
//        simpleMailMessage.setText("<a href=\"http://localhost:8080/confirm/email?token="+emailToken.getId()+">"+"인증 링크</a>");
        emailSenderService.sendEmail(mimeMessage);
        return emailToken.getId();
    }

    // 유효한 토큰정보 가져오기
    public EmailToken getEmailToken(String emailTokenId) {
        return emailRepository.findByIdAndExpired(
                emailTokenId,
                "false"
        ).orElseThrow(() -> {
            throw new NotFoundEmailTokenInfoException();
        });
    }
}
