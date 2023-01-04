package com.example.hospitalproject.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserSignInResponseDto {

    private String originToken;

    private String refreshToken;
}
