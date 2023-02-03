package com.example.hospitalproject.Dto.Payment.Card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayChargeRequestDto {
    @NotNull(message = "결제 금액을 입력해주세요.")
    private int pay;

    @NotBlank(message = "결제 하실 방법을 선택해주세요.")
    private String code;
}
