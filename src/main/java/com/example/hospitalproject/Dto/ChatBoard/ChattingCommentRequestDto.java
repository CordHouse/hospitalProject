package com.example.hospitalproject.Dto.ChatBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChattingCommentRequestDto {
    @NotNull(message = "대화 내용을 입력해주세요.")
    private String comment;
}