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
    private String sender;
    private String receiver;
    private LocalDateTime timeStamp;
    private long chatBoardId;

    public ChattingCommentResponseDto toDo(Chatting chatting){
        return new ChattingCommentResponseDto(chatting.getId(), chatting.getComment(), chatting.getSender(), chatting.getReceiver(), chatting.getDate(), chatting.getChatBoard().getId());
    }
}
