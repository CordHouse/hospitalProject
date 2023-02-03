package com.example.hospitalproject.Dto.Payment.Card.Format;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

/**
 * 단위 변환시 사용 (천원단위로 변환)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomDecimalFormat {
    private final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private int pay;
    public String getPaySplit(){
        return decimalFormat.format(pay)+"원";
    }
}
