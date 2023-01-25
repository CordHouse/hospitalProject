package com.example.hospitalproject.Dto.Payment.Card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.DecimalFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayChargeRequestDto {
    private final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    @NotNull(message = "결제 금액을 입력해주세요.")
    private int pay;

    @NotBlank(message = "결제 하실 방법을 선택해주세요.")
    private String Code;

    public String getPaySplit(){
        return decimalFormat.format(pay)+"원";
    }
}
