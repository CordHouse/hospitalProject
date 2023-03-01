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
public class UserIdSearchRequestDto {
    @NotBlank(message = "사용자 아이디를 찾기 위한 사용자 전화번호를 입력해주세요")
    @Pattern(regexp = "\\d{2,3}-\\d{3,4}-\\d{4}", message = "입력하신 전화번호를 다시 한 번 확인해주세요")
    @ApiModelProperty(value = "사용자의 아이디를 찾기 위한 전화번호를 입력", example = "010-1234-5678")
    private String phone;

    @NotBlank(message = "사용자 아이디를 찾기 위한 이메일을 입력해주세요")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "입력하신 이메일을 다시 한 번 확인해주세요")
    @ApiModelProperty(value = "사용자의 아이디를 찾기 위한 이메일을 입력", example = "test123@test.com")
    private String email;
}
