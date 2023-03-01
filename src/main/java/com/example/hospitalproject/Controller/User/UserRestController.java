package com.example.hospitalproject.Controller.User;

import com.example.hospitalproject.Dto.Token.TokenReIssueDto;
import com.example.hospitalproject.Dto.User.*;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Response.Response;
import com.example.hospitalproject.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class UserRestController {
    private final UserService userService;
    private final UserRepository userRepository;
    /*
    * 회원 가입 로직
    * */
    @PostMapping("/user/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public Response signUp(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto){
        return Response.success(userService.signUp(userRegisterRequestDto));
    }

    /*
    * 로그인 로직
    * */
    @PostMapping("/user/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public Response signIn(@RequestBody @Valid UserSignInRequestDto requestDto) {
        return Response.success(userService.signIn(requestDto));
    }

    /*
    * 회원 탈퇴 로직
    */
    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser() {
        userService.deleteUser();
    }

    /*
    * 토큰 재발급 로직
    * */
    @PostMapping("/user/reissue")
    @ResponseStatus(HttpStatus.OK)
    public Response reIssue(@RequestBody @Valid TokenReIssueDto reIssueDto) {
        return Response.success(userService.reIssue(reIssueDto));
    }

    /*
    * 아이디 찾기 로직
    * */
    @GetMapping("/user/id")
    @ResponseStatus(HttpStatus.OK)
    public Response findUserId(@RequestBody @Valid UserIdSearchRequestDto requestDto) {
        return Response.success(userService.searchUserId(requestDto));
    }

    /*
    * 비밀번호 재발급 로직
    * */
    @GetMapping("/user/password/reissue")
    @ResponseStatus(HttpStatus.OK)
    public Response passwordReissue(@RequestBody @Valid UserPasswordReissueRequestDto requestDto) {
        return Response.success(userService.passwordReissue(requestDto));
    }

    /*
    * 비밀번호 변경 로직
    * */
    @PutMapping("/user/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid UserPasswordChangeRequestDto requestDto) {
        userService.changePassword(requestDto);
    }

    /*
    * 사용자 권한 조회 로직
    * */
    @PostMapping("/user/grade")
    @ResponseStatus(HttpStatus.OK)
    public Response searchUserGrade(@RequestBody @Valid UserGradeSearchRequestDto userGradeSearchRequestDto){
        return Response.success(userService.searchUserGrade(userGradeSearchRequestDto));
    }

    /**
     * 3개월 마다 주기적으로 비밀번호 변경 권유하는 로직
     */
    @PostMapping("/user/password/change/recycle")
    @ResponseStatus(HttpStatus.OK)
    public void passwordRecycle(@RequestBody @Valid UserPasswordChangeRecycleRequestDto userPasswordChangeRecycleRequestDto) {
        User user = userSessionInfo();
        userService.passwordRecycle(userPasswordChangeRecycleRequestDto, user);
    }

    private User userSessionInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
            throw new NotFoundUserException("해당 계정은 존재하지 않습니다.");
        });
    }
}
