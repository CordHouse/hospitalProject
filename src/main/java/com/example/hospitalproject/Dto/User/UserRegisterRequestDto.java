package com.example.hospitalproject.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]{2,10}", message = "아이디 형식을 지켜주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$", message = "8이상 20자리 특수문자, 영문, 숫자 조합")
    private String password;

    @NotBlank(message = "생일을 입력해주세요.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "생년월일은 xxxx-xx-xx 형식을 지켜주세요.")
    private String birthday;

    @NotBlank(message = "연락처를 입력해주세요.")
    @Pattern(regexp = "\\d{2,3}-\\d{3,4}-\\d{4}", message = "연락처는 0x-xxx-xxxx 또는 0xx-xxxx-xxxx의 형식을 지켜주세요.")
    private String phone;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일 형식을 지켜주세요.")
    private String email;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;
}
