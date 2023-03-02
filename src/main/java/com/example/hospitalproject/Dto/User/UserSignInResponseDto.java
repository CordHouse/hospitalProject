package com.example.hospitalproject.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInResponseDto {

    private String originToken;

    private String refreshToken;

    private String message;

    public UserSignInResponseDto toDo(String originToken, String refreshToken) {
        return new UserSignInResponseDto(originToken, refreshToken, "");
    }
}
