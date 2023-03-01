package com.example.hospitalproject.Dto.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordChangeRecycleRequestDto {
    @NotBlank(message = "이전 비밀번호를 입력해주세요.")
    @ApiModelProperty(value = "이전 비밀번호", example = "test123!@")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$",
            message = "비밀번호는 8이상 20자리 특수문자, 영문, 숫자 조합 형식을 지켜주세요.")
    private String beforePassword;

    @NotBlank(message = "변경하실 비밀번호를 입력해주세요.")
    @ApiModelProperty(value = "변경할 비밀번호", example = "test456!@")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$",
            message = "비밀번호는 8이상 20자리 특수문자, 영문, 숫자 조합 형식을 지켜주세요.")
    private String afterPassword;

    @NotBlank(message = "변경하실 비밀번호가 일치하지 않습니다.")
    @ApiModelProperty(value = "변경할 비밀번호 일치 확인", example = "test456!@")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$",
            message = "비밀번호는 8이상 20자리 특수문자, 영문, 숫자 조합 형식을 지켜주세요.")
    private String afterPasswordCheck;
}
