package com.example.hospitalproject.Controller.Email;

import com.example.hospitalproject.Dto.Email.CreateEmailRequestDto;
import com.example.hospitalproject.Service.Email.EmailService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.hospitalproject.Controller.Create.ControllerCreate.createEmailRequest;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmailControllerTest {
    @InjectMocks
    private EmailRestController emailRestController;

    @Mock
    private EmailService emailService;

    private MockMvc mockMvc;

    @BeforeEach
    public void mvcInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(emailRestController).build();
    }

    @DisplayName("이메일 링크 클릭 후 인증")
    @Test
    public void confirmEmailTest() throws Exception {
        // given
        String token = "bfdc196a-ee11-487c-a0e1-2ae05247491a";

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.get("/confirm/email")
                        .param("token", token)
        ).andExpect(status().isOk());

        // then
        verify(emailService).confirmEmail(token);
    }

    @DisplayName("회원가입 전 이메일 인증")
    @Test
    public void createEmailTokenTest() throws Exception {
        // given
        CreateEmailRequestDto createEmailRequestDto = createEmailRequest();

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.post("/home/user/sign-up/confirm/email/valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(createEmailRequestDto))
        ).andExpect(status().isOk());

        // then
        verify(emailService).createEmailToken(createEmailRequestDto);
    }
}
