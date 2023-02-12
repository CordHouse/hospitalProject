package com.example.hospitalproject.Dto.Payment.Card;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "은행 입력", example = "국민은행")
    @NotBlank(message = "은행을 입력해주세요.")
    private String bank;

    @ApiModelProperty(value = "카드번호 입력", example = "1234-1234-1234-1234")
    @NotBlank(message = "카드번호를 입력해주세요.")
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "카드번호 16자리를 입력해주세요.")
    private String cardNumber;

    @ApiModelProperty(value = "유효기간/ year", example = "25")
    @NotBlank(message = "유효기간을 입력해주세요.")
    @Pattern(regexp = "\\d{2}", message = "년도 2자리를 입력해주세요.")
    private String validYear;

    @ApiModelProperty(value = "유효기간/ month", example = "01")
    @NotBlank(message = "유효기간을 입력해주세요.")
    @Pattern(regexp = "\\d{2}", message = "월 2자리를 입력해주세요.")
    private String validMonth;

    @ApiModelProperty(value = "비밀번호 앞 두자리", example = "12")
    @NotBlank(message = "비밀번호 앞 두자리를 입력해주세요.")
    @Pattern(regexp = "\\d{2}", message = "비밀번호 앞 2자리를 입력해주세요.")
    private String password;

    @ApiModelProperty(value = "등록된 카드 사용 여부", example = "선택/미선택")
    @NotBlank(message = "등록할 카드를 바로 사용할지 선택해주세요.")
    private String selectCard;
}
