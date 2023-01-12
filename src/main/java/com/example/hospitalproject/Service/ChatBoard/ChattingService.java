package com.example.hospitalproject.Service.ChatBoard;

import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentRequestDto;
import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.Chatting;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChatBoardException;
import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import com.example.hospitalproject.Repository.ChatBoard.ChattingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final ChattingRepository chattingRepository;
    private final ChatBoardRepository chatBoardRepository;

    @Transactional
    public void chatRunStatus(Long id, ChattingCommentRequestDto chattingCommentRequestDto){
        ChatBoard chatBoard = chatBoardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundChatBoardException();
        });
        Chatting chatting = new Chatting(chattingCommentRequestDto.getComment(), chatBoard);
        chattingRepository.save(chatting);
    }
}
