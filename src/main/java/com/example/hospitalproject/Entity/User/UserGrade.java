package com.example.hospitalproject.Entity.User;

import java.util.Arrays;

public enum UserGrade {
    ADMIN("관리자"),
    MANAGER("매니저"),
    EXCELLENT_MEMBER("우수회원"),
    COMMON_MEMBER("일반회원");
    private String grade;

    UserGrade(String grade){
        this.grade = grade;
    }

    public static UserGrade findUserGrade(String keyCode){
        return Arrays.stream(UserGrade.values())
                .filter(userGrade -> userGrade.name().equals(keyCode))
                .findAny()
                .orElseThrow();
    }

    public String getGrade(){
        return grade;
    }

}
