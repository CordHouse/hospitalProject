package com.example.hospitalproject.Controller.User;

import com.example.hospitalproject.Dto.Token.TokenReIssueDto;
import com.example.hospitalproject.Dto.User.UserGradeSearchRequestDto;
import com.example.hospitalproject.Dto.User.UserIdSearchRequestDto;
import com.example.hospitalproject.Dto.User.UserPasswordChangeRequestDto;
import com.example.hospitalproject.Dto.User.UserPasswordReissueRequestDto;
import com.example.hospitalproject.Dto.User.UserRegisterRequestDto;
import com.example.hospitalproject.Dto.User.UserSignInRequestDto;
import com.example.hospitalproject.Response.Response;
import com.example.hospitalproject.Service.User.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class UserRestController {
    private final UserService userService;
    /*
    * 회원 가입 로직
    * */
    @PostMapping("/user/sign-up")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "회원 가입", notes = "등록되지 않은 사용자의 정보를 등록하여 서비스를 이용할 수 있도록 로직 수행(비회원 사용 가능)")
    @ApiImplicitParam(name = "userRegisterRequestDto", value = "회원 가입에 필요한 정보를 입력")
    public Response signUp(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto){
        return Response.success(userService.signUp(userRegisterRequestDto));
    }

    /*
    * 로그인 로직
    * */
    @PostMapping("/user/sign-in")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 통하여 등록된 회원인지, 아닌지를 판단하는 로직(비회원 사용 가능)")
    @ApiImplicitParam(name = "requestDto", value = "로그인에 필요한 정보를 입력")
    public Response signIn(@RequestBody @Valid UserSignInRequestDto requestDto) {
        return Response.success(userService.signIn(requestDto));
    }

    /*
    * 회원 탈퇴 로직
    */
    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "회원 탈퇴", notes = "가입된 사용자의 정보를 지우고 싶을 경우, 사용자의 정보를 지우는 로직(회원 사용 가능)")
    public void deleteUser() {
        userService.deleteUser();
    }

    /*
    * 토큰 재발급 로직
    * */
    @PostMapping("/user/reissue")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "토큰 재발행", notes = "만료 기간이 다 된 JWT 토큰을 재발급하여 로그인 상태를 연장할 수 있도록 함(회원만 사용 가능)")
    @ApiImplicitParam(name = "reIssueDto", value = "AccessToken의 유효 기간을 연장하기 위한 정보를 입력")
    public Response reIssue(@RequestBody @Valid TokenReIssueDto reIssueDto) {
        return Response.success(userService.reIssue(reIssueDto));
    }

    /*
    * 아이디 찾기 로직
    * */
    @GetMapping("/user/id")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "사용자 아이디 찾기", notes = "사용자의 아이디를 찾는 로직(비회원 사용 가능)")
    @ApiImplicitParam(name = "requestDto", value = "사용자의 아이디를 찾기 위한 정보를 입력")
    public Response findUserId(@RequestBody @Valid UserIdSearchRequestDto requestDto) {
        return Response.success(userService.searchUserId(requestDto));
    }

    /*
    * 비밀번호 재발급 로직
    * */
    @GetMapping("/user/password/reissue")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "비밀번호 재발급", notes = "비밀번호를 재발급받는 로직, 회원가입시 입력하였던 이메일로 임시 비밀번호가 전송됩니다.(회원만 사용 가능)")
    @ApiImplicitParam(name = "requestDto", value = "사용자의 비밀번호를 재발급받기 위한 정보를 입력")
    public Response passwordReissue(@RequestBody @Valid UserPasswordReissueRequestDto requestDto) {
        return Response.success(userService.passwordReissue(requestDto));
    }

    /*
    * 비밀번호 변경 로직
    * */
    @PutMapping("/user/password")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호를 변경하는 로직(회원만 사용 가능)")
    @ApiImplicitParam(name = "requestDto", value = "사용자의 비밀번호를 변경하기 위한 정보를 입력")
    public void changePassword(@RequestBody @Valid UserPasswordChangeRequestDto requestDto) {
        userService.changePassword(requestDto);
    }

    /*
    * 사용자 권한 조회 로직
    * */
    @PostMapping("/user/grade")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "사용자 권한 확인", notes = "특정 사용자의 권한을 확인하는 로직(회원만 사용 가능)")
    @ApiImplicitParam(name = "userGradeSearchRequestDto", value = "특정 사용자의 권한을 확인하기 위한 정보를 입력")
    public Response searchUserGrade(@RequestBody @Valid UserGradeSearchRequestDto userGradeSearchRequestDto){
        return Response.success(userService.searchUserGrade(userGradeSearchRequestDto));
    }
}
