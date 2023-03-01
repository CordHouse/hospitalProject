package com.example.hospitalproject.Dto.Token;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenReIssueDto {
    @ApiModelProperty(value = "AccessToken의 유효 기간을 연장하기 위한 originToken", example = "originToken")
    @NotBlank(message = "originToken 값을 입력해주세요")
    private String originToken;

    @ApiModelProperty(value = "AccessToken의 유효 기간을 연장하기 위한 refreshToken", example = "refreshToken")
    @NotBlank(message = "refreshToken 값을 입력해주세요")
    private String refreshToken;
}
