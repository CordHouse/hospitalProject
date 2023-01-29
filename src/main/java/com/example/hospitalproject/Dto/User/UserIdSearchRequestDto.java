package com.example.hospitalproject.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserIdSearchRequestDto {
    @NotBlank(message = "사용자 아이디를 찾기 위한 사용자 전화 번호를 입력해주세요")
    private String phone;

    @NotBlank(message = "사용자 아이디를 찾기 위한 이메일을 입력해주세요")
    private String email;
}
