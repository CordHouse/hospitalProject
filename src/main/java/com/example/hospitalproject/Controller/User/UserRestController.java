package com.example.hospitalproject.Controller.User;

import com.example.hospitalproject.Dto.User.UserRegisterRequestDto;
import com.example.hospitalproject.Dto.User.UserGradeSearchRequestDto;
import com.example.hospitalproject.Dto.User.UserSignInRequestDto;
import com.example.hospitalproject.Response.Response;
import com.example.hospitalproject.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class UserRestController {
    private final UserService userService;
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public Response signUp(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto){
        return Response.success(userService.signUp(userRegisterRequestDto));
    }

    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    public Response signIn(@RequestBody @Valid UserSignInRequestDto requestDto) {
        return Response.success(userService.signIn(requestDto));
    }

    @PostMapping("/search/user/grade")
    @ResponseStatus(HttpStatus.OK)
    public Response searchUserGrade(@RequestBody @Valid UserGradeSearchRequestDto userGradeSearchRequestDto){
        return Response.success(userService.searchUserGrade(userGradeSearchRequestDto));
    }
}
