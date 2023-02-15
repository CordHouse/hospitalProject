package com.example.hospitalproject.Dto.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmailRequestDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
}
