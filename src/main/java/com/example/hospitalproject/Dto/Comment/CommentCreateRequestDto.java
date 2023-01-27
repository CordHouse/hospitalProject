package com.example.hospitalproject.Dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data   //게터 세터
@AllArgsConstructor
@NoArgsConstructor
//3개 합쳐서 앞글자 따서 "DNA"
public class CommentCreateRequestDto {
    @NotBlank(message = "댓글을 작성해주세요.")
    private String comment;
}
