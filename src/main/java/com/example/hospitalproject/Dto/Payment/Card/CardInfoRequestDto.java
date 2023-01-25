package com.example.hospitalproject.Dto.Payment.Card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoRequestDto {
    @NotBlank(message = "은행을 입력해주세요.")
    private String bank;

    @NotBlank(message = "카드번호를 입력해주세요.")
    private String cardNumber;

    @NotBlank(message = "유효기간을 입력해주세요.")
    private String validYear;

    @NotBlank(message = "유효기간을 입력해주세요.")
    private String validMonth;

    @NotBlank(message = "비밀번호 앞 두자리를 입력해주세요.")
    private String password;
}
