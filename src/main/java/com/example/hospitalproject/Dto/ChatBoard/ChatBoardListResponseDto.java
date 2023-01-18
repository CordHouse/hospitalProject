package com.example.hospitalproject.Dto.ChatBoard;

import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatBoardListResponseDto {
    private Long id;
    private String chatTitle;
    private String host;
    private String target;
    private String chattingType;
    private LocalDate createDate;

    public ChatBoardListResponseDto toDo(ChatBoard chatBoard){
        return new ChatBoardListResponseDto(chatBoard.getId(), chatBoard.getTitle(), chatBoard.getHost(), chatBoard.getTarget(), chatBoard.getChatTitleType().toString(), chatBoard.getCreateDate());
    }
}
