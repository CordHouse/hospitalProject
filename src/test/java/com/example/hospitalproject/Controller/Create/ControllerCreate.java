package com.example.hospitalproject.Controller.Create;

import com.example.hospitalproject.Entity.User.RoleUserGrade;
import com.example.hospitalproject.Entity.User.User;
import lombok.Builder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

@Builder
public class ControllerCreate {
    public static User createUser(){
        return new User("Test", "Tester", "123456a!!", "1999-01-01", "010-1234-1234", "qqq@naver.com", "경기도 수원시", RoleUserGrade.ROLE_COMMON_MEMBER);
    }

    public static Authentication createToken() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(createUser().getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
