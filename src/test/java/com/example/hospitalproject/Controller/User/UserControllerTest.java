package com.example.hospitalproject.Controller.User;

import com.example.hospitalproject.Dto.Token.TokenReIssueDto;
import com.example.hospitalproject.Dto.User.*;
import com.example.hospitalproject.Service.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void initMockmvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userRestController)
                .build();
    }


    @Test
    @DisplayName(value = "사용자 로그인 테스트")
    public void user_LoginTest() throws Exception {
        //given
        UserSignInRequestDto requestDto = new UserSignInRequestDto();
        requestDto.setUsername("TestUser");
        requestDto.setPassword("1234");

        //stub
        given(userService.signIn(requestDto))
                .willReturn(new UserSignInResponseDto("OriginToken", "RefreshToken", ""));

        //when
        mockMvc.perform(post("/home/user/sign-in")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.data.originToken").value("OriginToken"))
                .andExpect(jsonPath("$.result.data.refreshToken").value("RefreshToken"))
                .andExpect(status().isOk());

        //then
        verify(userService).signIn(requestDto);

    }

    @Test
    @DisplayName(value = "사용자 회원가입 테스트")
    public void user_SignUpTest() throws Exception {

        //given
        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        requestDto.setUsername("TestUser");
        requestDto.setName("테스트");
        requestDto.setPassword("abc123!@#");
        requestDto.setBirthday("9999-12-31");
        requestDto.setEmail("test@gmail.com");
        requestDto.setPhone("010-1234-5678");
        requestDto.setAddress("서울특별시");

        //stub

        //when
        mockMvc.perform(post("/home/user/sign-up")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(userService).signUp(requestDto);
    }

    @Test
    @DisplayName(value = "회원 탈퇴 테스트")
    public void user_DeleteTest_User() throws Exception {
        //given

        //stub

        //when
        mockMvc.perform(delete("/home/user"))
                .andExpect(status().isOk());

        //then
        verify(userService).deleteUser();
    }

    @Test
    @DisplayName(value = "토큰 재발급 테스트")
    public void Token_ReIssueTest_User() throws Exception {
        //given
        TokenReIssueDto reIssueDto = TokenReIssueDto.builder()
                .originToken("OriginToken")
                .refreshToken("RefreshToken")
                .build();

        //stub
        given(userService.reIssue(reIssueDto)).willReturn(reIssueDto);

        //when
        mockMvc.perform(post("/home/user/reissue")
                .content(objectMapper.writeValueAsString(reIssueDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.data.originToken").value("OriginToken"))
                .andExpect(jsonPath("$.result.data.refreshToken").value("RefreshToken"))
                .andExpect(status().isOk());

        //then
        verify(userService).reIssue(reIssueDto);
    }

    @Test
    @DisplayName(value = "아이디 찾기 테스트")
    public void find_UserIdTest() throws Exception {
        //given
        UserIdSearchRequestDto requestDto = new UserIdSearchRequestDto("010-1234-5678", "test@gamil.com");
        String findUser = "TestUser";

        //stub
        given(userService.searchUserId(requestDto)).willReturn(findUser);

        //when
        mockMvc.perform(get("/home/user/id")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.data").value(findUser))
                .andExpect(status().isOk());

        //then
        verify(userService).searchUserId(requestDto);
    }

    @Test
    @DisplayName(value = "비밀번호 재발급 테스트")
    public void password_ReIssueTest() throws Exception {
        //given
        UserPasswordReissueRequestDto requestDto =
                new UserPasswordReissueRequestDto("TestUser", "test@gmail.com", "010-1234-5678");

        //stub
        given(userService.passwordReissue(requestDto)).willReturn(anyString());

        //when
        mockMvc.perform(get("/home/user/password/reissue")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.data").isString())
                .andExpect(status().isOk());

        //then
        verify(userService).passwordReissue(requestDto);
    }

    @Test
    @DisplayName(value = "사용자 권한 조회 테스트")
    public void get_UserAuthorityTest_User() throws Exception {
        //given
        UserGradeSearchRequestDto requestDto = new UserGradeSearchRequestDto("TestUser");

        //stub
        given(userService.searchUserGrade(requestDto)).willReturn(anyString());

        //when
        mockMvc.perform(post("/home/user/grade")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.data").isString())
                .andExpect(status().isOk());

        //then
        verify(userService).searchUserGrade(requestDto);
    }
}
