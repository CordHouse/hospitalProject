package com.example.hospitalproject.Service.Email;

import com.example.hospitalproject.Dto.Email.CreateEmailRequestDto;
import com.example.hospitalproject.Entity.Email.EmailToken;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Repository.Email.EmailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.hospitalproject.Controller.Create.ControllerCreate.createUser;
import static com.example.hospitalproject.Service.Create.ServiceCreate.createEmailToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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


    @DisplayName("전달된 이메일 링크 클릭 후 토큰 검증")
    @Test
    public void confirmEmailTest() {
        // given
        String token = "bfdc196a-ee11-487c-a0e1-2ae05247491a";
        EmailToken emailToken = createEmailToken(token);

        given(emailRepository.findByIdAndExpirationDateAfterAndExpired(
                token,
                LocalDateTime.of(2023,2,24,21,40,0),
                "false"
        )).willReturn(Optional.of(emailToken));

        // when
        emailService.confirmEmail(token);

        // then
        assertThat(emailToken.getExpired()).isEqualTo("true");
    }

    @DisplayName("토큰 정보를 담는 이메일 생성")
    @Test
    public void createEmailTokenTest() {
        // given
        User user = createUser();
        EmailToken emailToken = new EmailToken(user.getUsername());
        CreateEmailRequestDto createEmailRequestDto = CreateEmailRequestDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        given(emailRepository.findByUsername(user.getUsername())).willReturn(Optional.of(emailToken));

        // when
        String id = emailService.createEmailToken(createEmailRequestDto);

        // then
        verify(emailSenderService).sendEmail(mimeMessage);
        assertThat(id).isEqualTo(any());
    }
}
