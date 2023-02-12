package com.example.hospitalproject.Dto.ChatBoard;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditPrivateTitleRequestDto {
    @ApiModelProperty(value = "제목 변경", example = "Test 모임")
    @NotBlank(message = "변경할 제목을 입력해주세요.")
    private String changeTitle;
}
