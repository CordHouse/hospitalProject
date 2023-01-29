package com.example.hospitalproject.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class UserPasswordChangeRequestDto {
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$",
            message = "비밀번호는 8이상 20자리 특수문자, 영문, 숫자 조합 형식을 지켜주세요.")
    private String password;
}
