package com.example.hospitalproject.Entity.Payment;

import java.util.Arrays;

public enum GroupCode {
    CARD(100),
    GIFT_CARD(200),
    CASH(300),
    ERROR(0);

    private int groupCode;

    GroupCode(int groupCode){
        this.groupCode = groupCode;
    }

    public static GroupCode findGroupCode(int code){
        return Arrays.stream(GroupCode.values())
                .filter(group -> group.getGroupCode() == code)
                .findAny()
                .orElse(ERROR);
    }

    public int getGroupCode(){
        return groupCode;
    }
}
