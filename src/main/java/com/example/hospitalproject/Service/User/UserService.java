package com.example.hospitalproject.Service.User;

import com.example.hospitalproject.Config.jwt.TokenProvider;
import com.example.hospitalproject.Dto.Token.RefreshTokenDto;
import com.example.hospitalproject.Dto.Token.TokenReIssueDto;
import com.example.hospitalproject.Dto.User.*;
import com.example.hospitalproject.Entity.Email.EmailToken;
import com.example.hospitalproject.Entity.User.RefreshToken;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import com.example.hospitalproject.Exception.Email.NotValidEmailException;
import com.example.hospitalproject.Exception.RefreshToken.NotFoundRefreshTokenException;
import com.example.hospitalproject.Exception.UserException.LoginFailureException;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Exception.UserException.UserInfoDuplicationException;
import com.example.hospitalproject.Repository.Email.EmailRepository;
import com.example.hospitalproject.Repository.RefreshToken.RefreshTokenRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final EmailRepository emailRepository;

    @Transactional
    public String signUp(UserRegisterRequestDto userRegisterRequestDto){
        if(userRepository.existsUserByUsernameOrEmailOrPhone(userRegisterRequestDto.getUsername(),
                userRegisterRequestDto.getEmail(), userRegisterRequestDto.getPhone()))
            throw new UserInfoDuplicationException("이미 가입된 사용자의 정보를 입력하셨습니다. 아이디, Email, 전화 번호를 다시 한번 확인해주세요.");

        User user = new User(
                userRegisterRequestDto.getName(),
                userRegisterRequestDto.getUsername(),
                passwordEncoder.encode(userRegisterRequestDto.getPassword()),
                userRegisterRequestDto.getBirthday(),
                userRegisterRequestDto.getPhone(),
                userRegisterRequestDto.getEmail(),
                userRegisterRequestDto.getAddress(),
                RoleUserGrade.ROLE_COMMON_MEMBER);

        EmailToken emailToken = emailRepository.findByUsername(userRegisterRequestDto.getUsername()).orElseThrow(() -> {
            throw new NotValidEmailException("이메일을 인증을 먼저 진행해주세요.");
        });
        if(emailToken.getExpired().equals("false")){
            throw new NotValidEmailException("이메일이 인증되지 않았습니다.");
        }
        userRepository.save(user);
        return "가입 성공";
    }

    @Transactional
    public UserSignInResponseDto signIn(UserSignInRequestDto requestDto) {
        User findUser = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(LoginFailureException::new);

        if(!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new LoginFailureException();
        }

        UsernamePasswordAuthenticationToken authenticationToken = requestDto.getAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        RefreshTokenDto createdToken = tokenProvider.createToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .id(authentication.getName()).value(createdToken.getRefreshToken()).build();

        refreshTokenRepository.save(refreshToken);

        return new UserSignInResponseDto(createdToken.getOriginToken(), createdToken.getRefreshToken());
    }

    @Transactional
    public void deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        userRepository.deleteUserByUsername(userName);
    }

    @Transactional
    public TokenReIssueDto reIssue(TokenReIssueDto reIssueDto) {
        Authentication authentication = tokenProvider.getAuthentication(reIssueDto.getOriginToken());

        if(!refreshTokenRepository.existsRefreshTokenById(authentication.getName()))
            throw new NotFoundRefreshTokenException();

        RefreshTokenDto refreshTokenDto = tokenProvider.createToken(authentication);

        return TokenReIssueDto.builder()
                .originToken(refreshTokenDto.getOriginToken())
                .refreshToken(refreshTokenDto.getRefreshToken())
                .build();
    }

    @Transactional(readOnly = true)
    public String searchUserId(UserIdSearchRequestDto requestDto) {
        User findUser = userRepository.findUserByEmailAndPhone(requestDto.getEmail(), requestDto.getPhone())
                .orElseThrow(() -> new NotFoundUserException("해당 정보와 일치하는 사용자가 존재하지 않습니다."));

        return findUser.getUsername();
    }

    @Transactional
    public String passwordReissue(UserPasswordReissueRequestDto requestDto) {
        User findUser = userRepository
                .findUserByEmailAndPhoneAndUsername(requestDto.getEmail(), requestDto.getPhone(), requestDto.getUsername())
                .orElseThrow(() -> new NotFoundUserException("해당 정보와 일치하는 사용자가 존재하지 않습니다."));

        String randomNumber = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10) + "!@#";

        findUser.setPassword(passwordEncoder.encode(randomNumber));

        return randomNumber;
    }

    @Transactional
    public void changePassword(UserPasswordChangeRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User findUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NotFoundUserException("현재 로그인한 사용자와 일치하는 정보가 없습니다."));

        findUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
    }

    @Transactional
    public String searchUserGrade(UserGradeSearchRequestDto userGradeSearchRequestDto){
        User user = userRepository.findByUsername(userGradeSearchRequestDto.getUsername()).orElseThrow();
        return RoleUserGrade.findUserGrade(user.getRoleUserGrade().toString()).getGrade();
    }
}
