package com.example.hospitalproject.Dto.Payment.Card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardInfoRequestDto {
    @NotBlank(message = "은행을 입력해주세요.")
    private String bank;

    @NotBlank(message = "카드번호를 입력해주세요.")
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "카드번호 16자리를 입력해주세요.")
    private String cardNumber;

    @NotBlank(message = "유효기간을 입력해주세요.")
    @Pattern(regexp = "\\d{2}", message = "년도 2자리를 입력해주세요.")
    private String validYear;

    @NotBlank(message = "유효기간을 입력해주세요.")
    @Pattern(regexp = "\\d{2}", message = "월 2자리를 입력해주세요.")
    private String validMonth;

    @NotBlank(message = "비밀번호 앞 두자리를 입력해주세요.")
    @Pattern(regexp = "\\d{2}", message = "비밀번호 앞 2자리를 입력해주세요.")
    private String password;

    @NotBlank(message = "등록할 카드를 바로 사용할지 선택해주세요.")
    private String selectCard;
}
