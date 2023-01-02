package com.example.hospitalproject.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGradeSearchRequestDto {
    @NotBlank(message = "닉네임을 작성해주세요.")
    private String username;
}
