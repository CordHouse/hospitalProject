package com.example.hospitalproject.Controller.Email;

import com.example.hospitalproject.Dto.Email.CreateEmailRequestDto;
import com.example.hospitalproject.Response.Response;
import com.example.hospitalproject.Service.Email.EmailService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class EmailRestController {
    private final EmailService emailService;

    /**
     * 이메일 인증 링크 클릭시 보이는 인증 처리
     */
    @ApiOperation(value = "이메일 인증 링크 클릭", notes = "이메일 인증 클릭시 인증처리")
    @GetMapping("/confirm/email")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParam(name = "token", value = "이메일 인증시 생성된 토큰 정보", example = "uuid2 (Universally Unique Identifier)")
    public String confirmEmail(@RequestParam String token) {
        emailService.confirmEmail(token);
        return "이메일 인증이 정상 처리되었습니다.";
    }

    /**
     * 회원가입 전 이메일 인증
     */
    @ApiOperation(value = "회원가입 전 이메일 인증", notes = "회원가입 전에 이메일 인증 진행 (본인확인)" +
            ", 회원가입시 작성되는 아이디와 이메일을 변수로 가져와 사용해야함 (따로 입력받는게 아님)")

    @PostMapping("/home/user/sign-up/confirm/email/valid")
    @ResponseStatus(HttpStatus.OK)
    public Response createEmailToken(@RequestBody @Valid CreateEmailRequestDto createEmailRequestDto) {
        return Response.success(emailService.createEmailToken(createEmailRequestDto));
    }
}
