package com.example.hospitalproject.Dto.ChatBoard;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChattingCommentRequestDto {
    @ApiModelProperty(value = "대화내용 입력", example = "Test")
    @NotNull(message = "대화 내용을 입력해주세요.")
    private String comment;
}
