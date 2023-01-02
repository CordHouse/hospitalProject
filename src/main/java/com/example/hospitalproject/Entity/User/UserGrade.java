package com.example.hospitalproject.Entity.User;

import java.util.Arrays;

public enum UserGrade {
    ADMIN("ADMIN","관리자"),
    MANAGER("MANAGER","매니저"),
    EXCELLENT_MEMBER("EXCELLENT_MEMBER","우수회원"),
    COMMON_MEMBER("COMMON_MEMBER","일반회원");

    private String keyCode;
    private String grade;

    UserGrade(String keyCode, String grade){
        this.keyCode = keyCode;
        this.grade = grade;
    }

    public static UserGrade findUserGrade(String keyCode){
        return Arrays.stream(UserGrade.values())
                .filter(userGrade -> userGrade.keyCode.equals(keyCode))
                .findAny()
                .orElseThrow();
    }

    public String getGrade(){
        return grade;
    }

}
