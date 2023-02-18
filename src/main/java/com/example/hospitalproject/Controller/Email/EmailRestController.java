package com.example.hospitalproject.Controller.Email;

import com.example.hospitalproject.Dto.Email.CreateEmailRequestDto;
import com.example.hospitalproject.Response.Response;
import com.example.hospitalproject.Service.Email.EmailService;
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
    @GetMapping("/confirm/email")
    @ResponseStatus(HttpStatus.OK)
    public String confirmEmail(@RequestParam String token) {
        emailService.confirmEmail(token);
        return "이메일 인증이 정상 처리되었습니다.";
    }

    /**
     * 회원가입 전 이메일 인증
     */
    @PostMapping("/home/user/sign-up/confirm/email/valid")
    @ResponseStatus(HttpStatus.OK)
    public Response createEmailToken(@RequestBody @Valid CreateEmailRequestDto createEmailRequestDto) {
        return Response.success(emailService.createEmailToken(createEmailRequestDto.getUsername(), createEmailRequestDto.getEmail()));
    }
}
