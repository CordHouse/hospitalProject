package com.example.hospitalproject.Dto.Token;

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
    @NotBlank(message = "originToken 값을 입력해주세요")
    private String originToken;

    @NotBlank(message = "refreshToken 값을 입력해주세요")
    private String refreshToken;
}
