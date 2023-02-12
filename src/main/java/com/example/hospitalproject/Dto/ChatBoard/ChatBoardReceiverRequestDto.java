package com.example.hospitalproject.Dto.ChatBoard;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatBoardReceiverRequestDto {
    @ApiModelProperty(value = "대화상대 입력", example = "Tester1")
    @NotNull(message = "대화할 상대방을 입력해주세요.")
    private String receiver;
}
