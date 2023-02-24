package com.example.hospitalproject.Service.Email;

import com.example.hospitalproject.Dto.Email.CreateEmailRequestDto;
import com.example.hospitalproject.Entity.Email.EmailToken;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.Email.NotFoundEmailTokenInfoException;
import com.example.hospitalproject.Repository.Email.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

import static com.example.hospitalproject.Service.Email.html.CreateEmailHtmlSource.emailHtmlSendSource;
import static com.example.hospitalproject.Service.Email.html.CreateEmailHtmlSource.passwordHtmlSendSource;

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
    public void confirmEmail(String token) {
        getEmailToken(token).setExpired("true");
    }

    /**
     * 토큰 정보를 담은 이메일 생성
     * SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
     * simpleMailMessage.setTo(email);
     * simpleMailMessage.setSubject("회원가입 이메일 인증");
     * simpleMailMessage.setText("<a href=\"http://localhost:8080/confirm/email?token="+emailToken.getId()+">"+"인증 링크</a>");
     */
    @Transactional
    public String createEmailToken(CreateEmailRequestDto createEmailRequestDto) {
        // 유저 아이디가 이미 존재한다면 기존 uuid 삭제 후 재발급
        EmailToken emailToken = doCreateEmailToken(createEmailRequestDto.getUsername());

        String toEmail = "http://localhost:8080/confirm/email?token="+emailToken.getId();
        String body = emailHtmlSendSource(toEmail);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.addRecipients(Message.RecipientType.TO, createEmailRequestDto.getEmail()); // 받는 사람 이메일
            mimeMessage.setSubject("FnDoc 회원가입 이메일 인증입니다."); // 메일 제목
            mimeMessage.setContent(body, "text/html;charset=UTF-8"); // 내용
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        emailSenderService.sendEmail(mimeMessage);
        return emailToken.getId();
    }

    // 유저아이디가 중복인 값 확인하기
    protected boolean emailTokenUsernameCheck(String username) {
        return emailRepository.findByUsername(username).isEmpty();
    }

    // 이메일 토큰 발급 진행하기 ( 중복된 유저가 없는 경우 )
    @Transactional
    protected EmailToken doCreateEmailToken(String username) {
        if(emailTokenUsernameCheck(username)) {
            return saveEmailToken(username);
        }
        emailRepository.deleteByUsername(username);
        return saveEmailToken(username);
    }

    // 이메일 토큰 발급 후 저장
    @Transactional
    protected EmailToken saveEmailToken(String username) {
        EmailToken emailToken = new EmailToken(username);
        emailRepository.save(emailToken);
        return emailToken;
    }

    // 유효한 토큰정보 가져오기
    public EmailToken getEmailToken(String emailTokenId) {
        return emailRepository.findByIdAndExpirationDateAfterAndExpired(
                emailTokenId,
                LocalDateTime.now(),
                "false"
        ).orElseThrow(() -> {
            throw new NotFoundEmailTokenInfoException();
        });
    }

    // 비밀번호 재발급 이메일 전송
    public void passwordReissueEmailSender(User user, String tempPassword) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String body = passwordHtmlSendSource(user.getUsername(), tempPassword);
        try {
            mimeMessage.addRecipients(Message.RecipientType.TO, user.getEmail()); // 받는 사람 이메일
            mimeMessage.setSubject("FnDoc 비밀번호 변경 인증 링크입니다."); // 메일 제목
            mimeMessage.setContent(body, "text/html;charset=UTF-8"); // 내용
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        emailSenderService.sendEmail(mimeMessage);
    }
}
