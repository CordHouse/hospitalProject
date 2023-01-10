package com.example.hospitalproject.Service.User;

import com.example.hospitalproject.Config.jwt.TokenProvider;
import com.example.hospitalproject.Dto.Token.RefreshTokenDto;
import com.example.hospitalproject.Dto.User.UserGradeSearchRequestDto;
import com.example.hospitalproject.Dto.User.UserRegisterRequestDto;
import com.example.hospitalproject.Dto.User.UserSignInRequestDto;
import com.example.hospitalproject.Dto.User.UserSignInResponseDto;
import com.example.hospitalproject.Entity.User.RefreshToken;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import com.example.hospitalproject.Exception.UserException.LoginFailureException;
import com.example.hospitalproject.Repository.RefreshToken.RefreshTokenRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public String signUp(UserRegisterRequestDto userRegisterRequestDto){
        User user = new User(
                userRegisterRequestDto.getName(),
                userRegisterRequestDto.getUsername(),
                passwordEncoder.encode(userRegisterRequestDto.getPassword()),
                userRegisterRequestDto.getBirthday(),
                userRegisterRequestDto.getPhone(),
                userRegisterRequestDto.getEmail(),
                userRegisterRequestDto.getAddress(),
                RoleUserGrade.ROLE_COMMON_MEMBER);
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
    public String searchUserGrade(UserGradeSearchRequestDto userGradeSearchRequestDto){
        User user = userRepository.findByUsername(userGradeSearchRequestDto.getUsername()).orElseThrow();
        return RoleUserGrade.findUserGrade(user.getRoleUserGrade().toString()).getGrade();
    }
}
