package com.example.hospitalproject.Authentication;

import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Repository.User.UserRepository;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Data
@Component
public class UsernameValid {
    private final UserRepository userRepository;

    public Authentication doAuthenticationUsernameCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
            throw new NotFoundUserException("해당 유저가 존재하지 않습니다.");
        });
        return authentication;
    }

    public User authenticationCheckReturnUserObject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
            throw new NotFoundUserException("해당 유저가 존재하지 않습니다.");
        });
    }
}
