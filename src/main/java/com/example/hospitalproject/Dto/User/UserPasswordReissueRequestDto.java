package com.example.hospitalproject.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordReissueRequestDto {
    @NotBlank(message = "사용자의 비밀번호를 재발급 받기 위한 사용자 아이디를 입력해주세요")
    private String username;

    @NotBlank(message = "사용자 비밀번호를 재발급 받기 위한 사용자 이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "사용자 비밀번호를 재발급 받기 위한 사용자 전화 번호를 입력해주세요")
    private String phone;
}
