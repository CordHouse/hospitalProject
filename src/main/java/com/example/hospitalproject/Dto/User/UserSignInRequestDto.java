package com.example.hospitalproject.Dto.User;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotNull;

@Data
public class UserSignInRequestDto {
    @ApiModelProperty(value = "로그인하기 위한 아이디 입력", example = "test1234")
    @NotNull(message = "로그인하기 위한 아이디를 입력해주세요")
    private String username;

    @ApiModelProperty(value = "로그인하기 위한 비밀 번호 입력", example = "test123!@#")
    @NotNull(message = "로그인하기 위한 비밀 번호를 입력해주세요")
    private String password;

    public UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.username, this.password);
    }
}
