package com.example.hospitalproject.Dto.ChatBoard;

import com.example.hospitalproject.Entity.Chatting.Chatting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChattingCommentResponseDto {
    private long id;
    private String text;
    private String host;
    private String target;
    private LocalDateTime timeStamp;
    private long chatBoardId;

    public ChattingCommentResponseDto toDo(Chatting chatting){
        return new ChattingCommentResponseDto(chatting.getId(), chatting.getComment(), chatting.getHost(), chatting.getTarget(), chatting.getDate(), chatting.getChatBoard().getId());
    }
}
