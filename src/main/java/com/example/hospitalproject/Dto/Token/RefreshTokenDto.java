package com.example.hospitalproject.Dto.Token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenDto {
    private String grantedType;
    private String originToken;
    private String refreshToken;
    private Long expirationTime;
}
