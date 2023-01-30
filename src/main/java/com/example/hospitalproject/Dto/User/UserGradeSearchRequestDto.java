package com.example.hospitalproject.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGradeSearchRequestDto {
    @NotBlank(message = "닉네임을 작성해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]{2,10}", message = "입력하신 아이디를 다시 한 번 확인해주세요")
    private String username;
}
