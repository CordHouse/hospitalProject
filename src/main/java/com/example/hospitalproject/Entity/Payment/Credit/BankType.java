package com.example.hospitalproject.Entity.Payment.Credit;

import java.util.Arrays;

public enum BankType {
    농협("농협"),
    우리은행("우리은행"),
    국민은행("국민은행"),
    notting("없음");

    private String bankName;

    BankType(String bankName){
        this.bankName = bankName;
    }

    public static BankType searchBankType(String bankName){
        return Arrays.stream(BankType.values())
                .filter(bank -> bank.name().equals(bankName))
                .findAny()
                .orElse(notting);
    }

    public String getBankName(){
        return bankName;
    }
}
