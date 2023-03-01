package com.example.hospitalproject.Dto.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordChangeRequestDto {
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$",
            message = "비밀번호는 8이상 20자리 특수문자, 영문, 숫자 조합 형식을 지켜주세요.")
    @ApiModelProperty(value = "변경하기 위한 비밀번호를 입력(이 때, 비밀번호는 영어 소, 대문자, 숫자 특수문자를 혼합한 8자 이상 20자리 미만이어야합니다.)"
            ,example = "test123!@#")
    private String password;
}
