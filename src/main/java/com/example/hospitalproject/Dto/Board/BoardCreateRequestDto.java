package com.example.hospitalproject.Dto.Board;

import com.example.hospitalproject.Exception.Board.NotInputStarPointException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "별점을 입력해주세요.")
    private String starPoint;

    public Double todo(Double d){
        d = Double.parseDouble(this.starPoint);

        if(d < 1.0 || d > 5.0) {
            throw new NotInputStarPointException();
        }

        return(Double.parseDouble(this.starPoint));
    }
}
