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
import com.example.hospitalproject.Exception.UserException.NotMatchPassword;
import com.example.hospitalproject.Exception.UserException.UserInfoDuplicationException;
import com.example.hospitalproject.Repository.Email.EmailRepository;
import com.example.hospitalproject.Repository.RefreshToken.RefreshTokenRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Service.Email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    private final EmailService emailService;

    @Transactional
    public String signUp(UserRegisterRequestDto userRegisterRequestDto){
        if(userRepository.existsUserByUsernameOrEmailOrPhone(userRegisterRequestDto.getUsername(),
                userRegisterRequestDto.getEmail(), userRegisterRequestDto.getPhone()))
            throw new UserInfoDuplicationException("?????? ????????? ???????????? ????????? ?????????????????????. ?????????, Email, ?????? ????????? ?????? ?????? ??????????????????.");

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
            throw new NotValidEmailException("???????????? ????????? ?????? ??????????????????.");
        });
        if(emailToken.getExpired().equals("false")){
            throw new NotValidEmailException("???????????? ???????????? ???????????????.");
        }
        userRepository.save(user);
        return "?????? ??????";
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

        // ????????? ?????? ???????????? ???????????? 3????????? ????????? ???????????? ?????? ?????? ?????? ?????????
        User user = userRepository.findByUsernameAndPasswordRecycleBefore(findUser.getUsername(), LocalDate.now());
        if(user != null){
            return new UserSignInResponseDto(createdToken.getOriginToken(), createdToken.getRefreshToken(), "??????????????? ??????????????????.");
        }
        return new UserSignInResponseDto().toDo(createdToken.getOriginToken(), createdToken.getRefreshToken());
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
                .orElseThrow(() -> new NotFoundUserException("?????? ????????? ???????????? ???????????? ???????????? ????????????."));

        return findUser.getUsername();
    }

    @Transactional
    public String passwordReissue(UserPasswordReissueRequestDto requestDto) {
        User findUser = userRepository
                .findUserByEmailAndPhoneAndUsername(requestDto.getEmail(), requestDto.getPhone(), requestDto.getUsername())
                .orElseThrow(() -> new NotFoundUserException("?????? ????????? ???????????? ???????????? ???????????? ????????????."));

        String randomNumber = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10) + "!@#";

        findUser.setPassword(passwordEncoder.encode(randomNumber));

        emailService.passwordReissueEmailSender(findUser, randomNumber);

        return randomNumber;
    }

    @Transactional
    public void changePassword(UserPasswordChangeRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User findUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NotFoundUserException("?????? ???????????? ???????????? ???????????? ????????? ????????????."));

        findUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
    }

    @Transactional
    public String searchUserGrade(UserGradeSearchRequestDto userGradeSearchRequestDto){
        User user = userRepository.findByUsername(userGradeSearchRequestDto.getUsername()).orElseThrow();
        return RoleUserGrade.findUserGrade(user.getRoleUserGrade().toString()).getGrade();
    }

    @Transactional
    public void passwordRecycle(UserPasswordChangeRecycleRequestDto userPasswordChangeRecycleRequestDto, User user) {
        if(!passwordEncoder.matches(user.getPassword(), userPasswordChangeRecycleRequestDto.getBeforePassword())) {
            throw new NotMatchPassword("?????? ??????????????? ???????????? ????????????.");
        }
        if(!userPasswordChangeRecycleRequestDto.getBeforePassword().equals(userPasswordChangeRecycleRequestDto.getAfterPassword())) {
            throw new NotMatchPassword("?????? ??????????????? ????????? ??????????????? ???????????????.");
        }
        if(!userPasswordChangeRecycleRequestDto.getAfterPassword().equals(userPasswordChangeRecycleRequestDto.getAfterPasswordCheck())) {
            throw new NotMatchPassword("????????? ??????????????? ???????????? ????????????.");
        }
        user.setPassword(passwordEncoder.encode(userPasswordChangeRecycleRequestDto.getAfterPassword()));
    }
}
