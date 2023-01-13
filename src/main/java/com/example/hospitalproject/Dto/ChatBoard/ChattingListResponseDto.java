package com.example.hospitalproject.Dto.ChatBoard;

import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChattingListResponseDto {
    private Long id;
    private String chatTitle;
    private String sender;
    private String receiver;
    private String chattingType;
    private LocalDate createDate;

    public ChattingListResponseDto toDo(ChatBoard chatBoard){
        return new ChattingListResponseDto(chatBoard.getId(), chatBoard.getTitle(), chatBoard.getSender(), chatBoard.getReceiver(), chatBoard.getChatTitleType().toString(), chatBoard.getCreateDate());
    }
}
