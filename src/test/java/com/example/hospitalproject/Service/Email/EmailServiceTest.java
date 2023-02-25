package com.example.hospitalproject.Service.Email;

import com.example.hospitalproject.Dto.Email.CreateEmailRequestDto;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.Email.NotFoundEmailTokenInfoException;
import com.example.hospitalproject.Repository.Email.EmailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.time.LocalDateTime;

import static com.example.hospitalproject.Controller.Create.ControllerCreate.createUser;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @SpyBean
    private LocalDateTime localDateTime;

    @DisplayName("전달된 이메일 링크 클릭 후 토큰 검증")
    @Test
    public void confirmEmailTest() {
        // given
        String token = "bfdc196a-ee11-487c-a0e1-2ae05247491a";
        // when

        // then
        assertThrows(NotFoundEmailTokenInfoException.class, () -> emailService.confirmEmail(token));
    }

    @DisplayName("토큰 정보를 담는 이메일 생성")
    @Test
    public void createEmailTokenTest() throws MessagingException {
        // given
        User user = createUser();
        CreateEmailRequestDto createEmailRequestDto = CreateEmailRequestDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        mimeMessage.addRecipients(Message.RecipientType.TO, createEmailRequestDto.getEmail());
        mimeMessage.setSubject("FnDoc 회원가입 이메일 인증입니다.");
        mimeMessage.setContent("test", "text/html;charset=UTF-8");
        given(javaMailSender.createMimeMessage()).willReturn(mimeMessage);
        // when
        emailService.createEmailToken(createEmailRequestDto);

        // then
        verify(emailSenderService).sendEmail(mimeMessage);
    }
}
