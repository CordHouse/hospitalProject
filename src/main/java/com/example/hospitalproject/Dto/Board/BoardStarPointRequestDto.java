package com.example.hospitalproject.Dto.Board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardStarPointRequestDto {
    @NotNull(message = "별점을 입력해주세요.")
    private Double starPoint;
}
