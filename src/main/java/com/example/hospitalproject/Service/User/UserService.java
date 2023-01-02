package com.example.hospitalproject.Service.User;

import com.example.hospitalproject.Dto.User.UserGradeSearchRequestDto;
import com.example.hospitalproject.Dto.User.UserRegisterRequestDto;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Entity.User.UserGrade;
import com.example.hospitalproject.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public String signUp(UserRegisterRequestDto userRegisterRequestDto){
        User user = new User(
                userRegisterRequestDto.getName(),
                userRegisterRequestDto.getUsername(),
                userRegisterRequestDto.getPassword(),
                userRegisterRequestDto.getBirthday(),
                userRegisterRequestDto.getPhone(),
                userRegisterRequestDto.getEmail(),
                userRegisterRequestDto.getAddress(),
                UserGrade.ADMIN.name());
        userRepository.save(user);
        return "가입 성공";
    }

    @Transactional
    public String searchUserGrade(UserGradeSearchRequestDto userGradeSearchRequestDto){
        User user = userRepository.findByUsername(userGradeSearchRequestDto.getUsername()).orElseThrow();
        return UserGrade.findUserGrade(user.getUserGrade()).getGrade();
    }
}
