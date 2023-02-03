package com.example.hospitalproject.Service.ChatBoard.Response;

import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.ChatTitleType;
import org.springframework.stereotype.Component;

@Component
public class ChatBoardTypeResponse {
    public ChatBoard createChatBoardPrivateInit(String host, String target) {
        return new ChatBoard(
                ChatTitleType.PRIVATE_CHAT.getValue(),
                ChatTitleType.PRIVATE_CHAT,
                host,
                target);
    }

    public ChatBoard createChatBoardServiceInit(String host, String target) {
        return new ChatBoard(
                ChatTitleType.SERVICE_CENTER.getValue(),
                ChatTitleType.SERVICE_CENTER,
                host,
                target);
    }
}
