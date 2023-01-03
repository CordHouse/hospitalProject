package com.example.hospitalproject.Service.ChatBoard;

import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.ChatTitleType;
import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatBoardService {
    private final ChatBoardRepository chatBoardRepository;

    @Transactional
    public void createServiceCenterChatBoard(){
        ChatBoard chatBoard = new ChatBoard(ChatTitleType.SERVICE_CENTER.name());
        chatBoardRepository.save(chatBoard);
    }

    @Transactional
    public void createPrivateChatBoard(){
        ChatBoard chatBoard = new ChatBoard(ChatTitleType.PRIVATE_CHAT.name());
        chatBoardRepository.save(chatBoard);
    }
}
