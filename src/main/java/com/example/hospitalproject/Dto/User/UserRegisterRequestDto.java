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
public class UserRegisterRequestDto {
    @ApiModelProperty(value = "회원 가입시에 필요한 사용자의 이름(5자 제한)", example = "홍길동")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @ApiModelProperty(value = "회원 가입시에 필요한 아이디(숫자, 영어 소문자, 대문자로 구성된 길이 2 이상 10미만의 아이디)", example = "test1234")
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]{2,10}", message = "아이디 형식을 지켜주세요.")
    private String username;

    @ApiModelProperty(value = "회원 가입시에 필요한 비밀번호(영어 소문자, 대문자, 숫자, 특수문자 등이 조합된 8자리 이상, 20자리 미만의 비밀번호)", example = "test123!@#")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$", message = "비밀번호는 8이상 20자리 특수문자, 영문, 숫자 조합 형식을 지켜주세요.")
    private String password;

    @ApiModelProperty(value = "회원 가입시에 필요한 사용자의 생년월일", example = "9999-12-31")
    @NotBlank(message = "생일을 입력해주세요.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "생년월일은 xxxx-xx-xx 형식을 지켜주세요.")
    private String birthday;

    @ApiModelProperty(value = "회원 가입시에 필요한 사용자의 연락처", example = "010-1234-5678")
    @NotBlank(message = "연락처를 입력해주세요.")
    @Pattern(regexp = "\\d{2,3}-\\d{3,4}-\\d{4}", message = "연락처는 0x-xxx-xxxx 또는 0xx-xxxx-xxxx의 형식을 지켜주세요.")
    private String phone;

    @ApiModelProperty(value = "회원 가입시에 필요한 사용자의 이메일", example = "test123@test.com")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일 형식을 지켜주세요.")
    private String email;

    @ApiModelProperty(value = "회원 가입시에 필요한 사용자의 주소", example = "서울특별시 XX구 XX동")
    @NotBlank(message = "주소를 입력해주세요.")
    private String address;
}
