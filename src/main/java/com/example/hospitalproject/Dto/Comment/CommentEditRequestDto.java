package com.example.hospitalproject.Dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEditRequestDto {
    @NotBlank(message = "수정할 댓글을 작성해주세요.")
    private String comment;
}
