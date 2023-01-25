package com.example.hospitalproject.Entity.Payment;

import com.example.hospitalproject.Exception.Payment.NotFoundPayTypeException;

import java.util.Arrays;
import java.util.List;

public enum Code {
    CODE_100(100, List.of("카카오페이", "신용카드", "체크카드")),
    CODE_200(200, List.of("문화상품권")),
    CODE_300(300, List.of("무통장 입금", "계좌이체", "토스")),
    CODE_0(0, List.of("미선택"));

    private int code;
    private List<String> type;

    Code(int code, List<String> type){
        this.code = code;
        this.type = type;
    }

    public static Code findCodeType(String type){
        return Arrays.stream(Code.values())
                .filter(code -> code.getType().contains(type))
                .findAny()
                .orElse(CODE_0);
    }

    public void checkType(String payType){
        if(!type.contains(payType)){
            throw new NotFoundPayTypeException();
        }
    }

    public List<String> getType(){
        return type;
    }

    public int getCode(){
        return code;
    }
}
