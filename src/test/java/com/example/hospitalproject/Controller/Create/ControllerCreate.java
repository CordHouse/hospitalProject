package com.example.hospitalproject.Controller.Create;

import com.example.hospitalproject.Entity.User.RoleUserGrade;
import com.example.hospitalproject.Entity.User.User;
import lombok.Builder;

@Builder
public class ControllerCreate {
    public static User createUser(){
        return new User("이지우", "jiwoo", "123456a!!", "1999-01-01", "010-1234-1234", "qqq@naver.com", "경기도 수원시", RoleUserGrade.ROLE_COMMON_MEMBER);
    }
}
