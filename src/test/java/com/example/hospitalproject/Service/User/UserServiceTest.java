package com.example.hospitalproject.Service.User;

import com.example.hospitalproject.Config.jwt.TokenProvider;
import com.example.hospitalproject.Dto.Token.RefreshTokenDto;
import com.example.hospitalproject.Dto.Token.TokenReIssueDto;
import com.example.hospitalproject.Dto.User.*;
import com.example.hospitalproject.Entity.Email.EmailToken;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import com.example.hospitalproject.Exception.Email.NotValidEmailException;
import com.example.hospitalproject.Exception.RefreshToken.NotFoundRefreshTokenException;
import com.example.hospitalproject.Exception.UserException.LoginFailureException;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Exception.UserException.UserInfoDuplicationException;
import com.example.hospitalproject.Repository.Email.EmailRepository;
import com.example.hospitalproject.Repository.RefreshToken.RefreshTokenRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Service.Email.EmailService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName(value = "회원 가입 테스트")
    public void userSignUpTest() {
        //given
        EmailToken emailToken = new EmailToken();
        emailToken.setId(UUID.randomUUID().toString());
        emailToken.setUsername("testUser");
        emailToken.setExpired("true");
        emailToken.setExpirationDate(LocalDateTime.now().plusYears(1L));

        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        requestDto.setUsername("testUser");
        requestDto.setName("test");
        requestDto.setBirthday("1999-03-09");
        requestDto.setEmail("testUser@gmail.com");
        requestDto.setPhone("010-1234-5678");
        requestDto.setAddress("서울특별시");
        requestDto.setPassword(passwordEncoder.encode("asdf!@#ASDF"));

        //stub
        given(emailRepository.findByUsername(emailToken.getUsername())).willReturn(Optional.of(emailToken));
        given(userRepository
                .existsUserByUsernameOrEmailOrPhone(
                        requestDto.getUsername(), requestDto.getEmail(), requestDto.getPhone()
                ))
                .willReturn(false);

        //when
        String resultString = userService.signUp(requestDto);

        //then
        Assertions.assertThat(resultString).isEqualTo("가입 성공");
    }

    @Test
    @DisplayName(value = "회원 가입 시, 중복 데이터로 인한 오류 테스트")
    public void userSignUpTest_UserInfoDuplicationExceptionTest() {
        //given
        EmailToken emailToken = new EmailToken();
        emailToken.setId(UUID.randomUUID().toString());
        emailToken.setUsername("testUser");
        emailToken.setExpired("true");
        emailToken.setExpirationDate(LocalDateTime.now().plusYears(1L));

        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        requestDto.setUsername("testUser");
        requestDto.setName("test");
        requestDto.setBirthday("1999-03-09");
        requestDto.setEmail("testUser@gmail.com");
        requestDto.setPhone("010-1234-5678");
        requestDto.setAddress("서울특별시");
        requestDto.setPassword(passwordEncoder.encode("asdf!@#ASDF"));

        //stub
        given(userRepository
                .existsUserByUsernameOrEmailOrPhone(
                        requestDto.getUsername(), requestDto.getEmail(), requestDto.getPhone()
                ))
                .willReturn(true);

        //when, then
        try {
            userService.signUp(requestDto);

            fail("중복된 데이터가 이미 데이터베이스에 존재합니다.");
        } catch(UserInfoDuplicationException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName(value = "회원 가입 시, 이메일 미인증으로 인한 오류 테스트")
    public void userSignUpTest_NotValidEmailExceptionTest() {
        //given
        EmailToken emailToken = new EmailToken();
        emailToken.setId(UUID.randomUUID().toString());
        emailToken.setUsername("testUser");
        emailToken.setExpired("true");
        emailToken.setExpirationDate(LocalDateTime.now().plusYears(1L));

        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        requestDto.setUsername("testUser");
        requestDto.setName("test");
        requestDto.setBirthday("1999-03-09");
        requestDto.setEmail("testUser@gmail.com");
        requestDto.setPhone("010-1234-5678");
        requestDto.setAddress("서울특별시");
        requestDto.setPassword(passwordEncoder.encode("asdf!@#ASDF"));

        //stub
        given(userRepository
                .existsUserByUsernameOrEmailOrPhone(
                        requestDto.getUsername(), requestDto.getEmail(), requestDto.getPhone()
                ))
                .willReturn(false);
        given(emailRepository.findByUsername(emailToken.getUsername()))
                .willReturn(Optional.ofNullable(null));

        //when, then
        try {
            userService.signUp(requestDto);

            fail("인증되지 않은 이메일입니다.");
        } catch(NotValidEmailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName(value = "회원 가입 시, 이메일 추가 인증 미실시로 인한 오류 테스트")
    public void userSignUpTest_NotValidEmailExceptionTest_2() {
        //given
        EmailToken emailToken = new EmailToken();
        emailToken.setId(UUID.randomUUID().toString());
        emailToken.setUsername("testUser");
        emailToken.setExpired("false");
        emailToken.setExpirationDate(LocalDateTime.now().plusYears(1L));

        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        requestDto.setUsername("testUser");
        requestDto.setName("test");
        requestDto.setBirthday("1999-03-09");
        requestDto.setEmail("testUser@gmail.com");
        requestDto.setPhone("010-1234-5678");
        requestDto.setAddress("서울특별시");
        requestDto.setPassword(passwordEncoder.encode("asdf!@#ASDF"));

        //stub
        given(userRepository
                .existsUserByUsernameOrEmailOrPhone(
                        requestDto.getUsername(), requestDto.getEmail(), requestDto.getPhone()
                ))
                .willReturn(false);
        given(emailRepository.findByUsername(emailToken.getUsername()))
                .willReturn(Optional.ofNullable(emailToken));

        //when, then
        try {
            userService.signUp(requestDto);

            fail("인증되지 않은 이메일입니다.");
        } catch(NotValidEmailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName(value = "가입되지 않은 사용자 정보로 로그인할 경우")
    public void userSignIn_LoginFailureExceptionTest() {
        //given
        UserSignInRequestDto requestDto = new UserSignInRequestDto();
        requestDto.setUsername("Test User1");
        requestDto.setPassword("1234");

        //stub
        given(userRepository.findByUsername(requestDto.getUsername())).willReturn(Optional.ofNullable(null));

        //when, then
        try {
            userService.signIn(requestDto);

            fail("등록되지 않은 사용자입니다.");
        } catch(LoginFailureException e) {
            System.out.println("등록되지 않은 사용자입니다.");
        }
    }

    @Test
    @DisplayName(value = "가입된 정보와 일치하지 않은 비밀 번호를 입력한경우")
    public void userSignIn_LoginFailureExceptionTest_password() {
        //given
        UserSignInRequestDto requestDto = new UserSignInRequestDto();
        requestDto.setUsername("Test User1");
        requestDto.setPassword("1234");

        com.example.hospitalproject.Entity.User.User user = new com.example.hospitalproject.Entity.User.User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(requestDto.getPassword());

        //stub
        given(userRepository.findByUsername(requestDto.getUsername()))
                .willReturn(Optional.of(user));
        given(passwordEncoder.matches(user.getPassword(), "1234")).willReturn(false);

        //when, then
        try {
            userService.signIn(requestDto);

            fail("비밀번호가 일치하지 않습니다.");
        } catch(LoginFailureException e) {
            System.out.println("비밀번호가 일치하지 않습니다.");
        }
    }

    @Test
    @DisplayName(value = "회원 탈퇴 테스트")
    public void userDeleteTest() {
        //given
        User user = new User("Test User", "", new ArrayList<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //stub
        BDDMockito.willDoNothing().given(userRepository).deleteUserByUsername(any());

        //when
        userService.deleteUser();

        //then
        verify(userRepository).deleteUserByUsername(any());
    }

    @Test
    @DisplayName(value = "토큰 재발급 테스트")
    public void tokenReissueTest() {
        //given
        User user = new User("Test User", "", new ArrayList<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenReIssueDto reIssueDto = TokenReIssueDto.builder()
                .originToken("OriginTokenValue")
                .refreshToken("RefreshTokenValue")
                .build();

        RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
                .originToken(reIssueDto.getOriginToken())
                .refreshToken(reIssueDto.getRefreshToken())
                .build();

        //stub
        given(tokenProvider.getAuthentication(reIssueDto.getOriginToken())).willReturn(authentication);
        given(refreshTokenRepository.existsRefreshTokenById(authentication.getName())).willReturn(true);
        given(tokenProvider.createToken(authentication)).willReturn(refreshTokenDto);

        //when
        TokenReIssueDto responseValue = userService.reIssue(reIssueDto);

        //then
        Assertions.assertThat(responseValue.getOriginToken()).isEqualTo(refreshTokenDto.getOriginToken());
        Assertions.assertThat(responseValue.getRefreshToken()).isEqualTo(refreshTokenDto.getRefreshToken());
    }

    @Test
    @DisplayName(value = "토큰 재발급시, 사용자의 정보가 등록되어 있지 않은 경우")
    public void tokenReissue_NotFoundRefreshTokenExceptionTest() {
        //given
        User user = new User("Test User", "", new ArrayList<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenReIssueDto reIssueDto = TokenReIssueDto.builder()
                .originToken("OriginTokenValue")
                .refreshToken("RefreshTokenValue")
                .build();

        //stub
        given(tokenProvider.getAuthentication(reIssueDto.getOriginToken())).willReturn(authentication);
        given(refreshTokenRepository.existsRefreshTokenById(authentication.getName())).willReturn(false);

        //when, then
        try {
            userService.reIssue(reIssueDto);

            fail("해당 사용자의 정보와 일치하는 재발급 토큰을 찾지 못하였습니다.");
        } catch(NotFoundRefreshTokenException e) {
            System.out.println("해당 사용자의 정보와 일치하는 재발급 토큰을 찾지 못하였습니다.");
        }
    }

    @Test
    @DisplayName(value = "아이디 찾기 테스트")
    public void findUserIdTest() {
        //given
        UserIdSearchRequestDto requestDto = new UserIdSearchRequestDto("010-1234-5678", "test@gmail.com");
        com.example.hospitalproject.Entity.User.User user = new
                com.example.hospitalproject.Entity.User.User();
        user.setPhone(requestDto.getPhone());
        user.setEmail(requestDto.getEmail());

        //stub
        given(userRepository.findUserByEmailAndPhone(requestDto.getEmail(), requestDto.getPhone()))
                .willReturn(Optional.of(user));

        //when
        userService.searchUserId(requestDto);

        //then
        verify(userRepository).findUserByEmailAndPhone(requestDto.getEmail(), requestDto.getPhone());
    }

    @Test
    @DisplayName(value = "아이디를 찾을 때, 입력한 정보와 일치하는 계정을 찾지 못한 경우")
    public void findUserId_NotFoundUserExceptionTest() {
        //given
        UserIdSearchRequestDto requestDto = new UserIdSearchRequestDto("010-1234-5678", "test@gmail.com");

        //stub
        given(userRepository.findUserByEmailAndPhone(requestDto.getEmail(), requestDto.getPhone()))
                .willReturn(Optional.ofNullable(null));

        //when, then
        try {
            userService.searchUserId(requestDto);

            fail("입력한 정보와 일치하는 사용자를 찾지 못하였습니다,.");
        } catch(NotFoundUserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName(value = "비밀번호 임시 발급 테스트")
    public void userPasswordReissueTest() {
        //given
        UserPasswordReissueRequestDto requestDto =
                new UserPasswordReissueRequestDto("Test User", "test@gmail.com", "010-1234-5678");

        com.example.hospitalproject.Entity.User.User user = new com.example.hospitalproject.Entity.User.User();
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPhone(requestDto.getPhone());

        String newPassword = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);

        //stub
        given(userRepository
                .findUserByEmailAndPhoneAndUsername(
                        requestDto.getEmail(), requestDto.getPhone(), requestDto.getUsername()
                )).willReturn(Optional.of(user));
        given(passwordEncoder.encode(anyString())).willReturn("1234");
        doNothing().when(emailService).passwordReissueEmailSender(user, "1234");

        //when
        userService.passwordReissue(requestDto);

        //then
        verify(emailService).passwordReissueEmailSender(user, "1234");
    }

    @Test
    @DisplayName(value = "비밀번호 임시 발급시, 입력한 정보와 일치하는 사용자가 존재하지 않을 경우")
    public void getUserPassword_NotFoundUserExceptionTest() {
        //given
        UserPasswordReissueRequestDto requestDto =
                new UserPasswordReissueRequestDto("Test User", "test@gmail.com", "010-1234-5678");

        //stub
        given(userRepository
                .findUserByEmailAndPhoneAndUsername(
                        requestDto.getEmail(), requestDto.getPhone(), requestDto.getUsername()
                )).willReturn(Optional.ofNullable(null));

        //when, then
        try {
            userService.passwordReissue(requestDto);

            fail("입력하신 정보와 일치하는 사용자를 찾지 못하였습니다.");
        } catch(NotFoundUserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName(value = "비밀번호 변경 테스트")
    public void changePasswordTest() {
        //given
        UserPasswordChangeRequestDto requestDto = new UserPasswordChangeRequestDto("1234");

        User user = new User("Test User", "", new ArrayList<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        com.example.hospitalproject.Entity.User.User findUser = new com.example.hospitalproject.Entity.User.User();
        findUser.setUsername("Test User");

        String newPassword = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);

        //stub
        given(userRepository.findByUsername(authentication.getName())).willReturn(Optional.of(findUser));
        given(passwordEncoder.encode(requestDto.getPassword())).willReturn(newPassword);

        //when
        userService.changePassword(requestDto);

        //then
        Assertions.assertThat(findUser.getPassword()).isEqualTo(newPassword);
    }

    @Test
    @DisplayName(value = "비밀번호 변경시, 현재 로그인한 사용자와 일치하는 정보를 찾을 수 없는 경우")
    public void changePassword_NotFoundUserExceptionTest() {
        //given
        UserPasswordChangeRequestDto requestDto = new UserPasswordChangeRequestDto("1234");

        User user = new User("Test User", "", new ArrayList<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //stub
        given(userRepository.findByUsername(authentication.getName())).willReturn(Optional.ofNullable(null));

        //when, then
        try {
            userService.changePassword(requestDto);

            fail("현재 로그인한 사용자와 일치하는 정보를 찾을 수 없습니다.");
        } catch(NotFoundUserException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName(value = "사용자 권한 확인 테스트")
    public void getUserAuthorityTest() {
        //given
        UserGradeSearchRequestDto requestDto = new UserGradeSearchRequestDto("Test User");

        com.example.hospitalproject.Entity.User.User findUser = new com.example.hospitalproject.Entity.User.User();
        findUser.setUsername(requestDto.getUsername());
        findUser.setRoleUserGrade(RoleUserGrade.ROLE_COMMON_MEMBER);

        //stub
        given(userRepository.findByUsername(requestDto.getUsername())).willReturn(Optional.of(findUser));

        //when
        userService.searchUserGrade(requestDto);

        //then
        verify(userRepository).findByUsername(requestDto.getUsername());
    }
}
