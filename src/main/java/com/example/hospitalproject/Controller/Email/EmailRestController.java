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

    @GetMapping("/confirm/email")
    @ResponseStatus(HttpStatus.OK)
    public Response confirmEmail(@RequestParam String token) {
        return Response.success(emailService.confirmEmail(token));
    }

    @PostMapping("/home/user/sign-up/confirm/email/valid")
    @ResponseStatus(HttpStatus.OK)
    public Response createEmailToken(@RequestBody @Valid CreateEmailRequestDto createEmailRequestDto) {
        return Response.success(emailService.createEmailToken(createEmailRequestDto.getUsername(), createEmailRequestDto.getEmail()));
    }
}
