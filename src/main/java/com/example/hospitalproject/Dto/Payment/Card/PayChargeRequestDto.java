package com.example.hospitalproject.Dto.Payment.Card;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "결제 금액", example = "10000")
    @NotNull(message = "결제 금액을 입력해주세요.")
    private int pay;

    @ApiModelProperty(value = "결제 방법", example = "신용카드/계좌이체/..")
    @NotBlank(message = "결제 하실 방법을 선택해주세요.")
    private String code;
}
