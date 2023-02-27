package com.example.hospitalproject.Dto.Email;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEmailRequestDto {
    @ApiModelProperty(value = "아이디 입력", example = "tester")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;
    @ApiModelProperty(value = "이메일 입력", example = "example@naver.com")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
}
