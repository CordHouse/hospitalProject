package com.example.hospitalproject.Service.ChatBoard;

import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.ChatBoardListResponseDto;
import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentResponseDto;
import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.Chatting;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChatBoardException;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChattingException;
import com.example.hospitalproject.Exception.ChatBoard.NotMatchSenderDeleteException;
import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import com.example.hospitalproject.Repository.ChatBoard.ChattingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final ChattingRepository chattingRepository;
    private final ChatBoardRepository chatBoardRepository;
    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Transactional
    public void chatRunStatus(Long id, ChattingCommentRequestDto chattingCommentRequestDto){
        ChatBoard chatBoard = chatBoardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundChatBoardException();
        });
        if(authentication.getName().equals(chatBoard.getHost())) {
            Chatting chatting = new Chatting(chattingCommentRequestDto.getComment(), chatBoard, chatBoard.getHost(), chatBoard.getTarget());
            chattingRepository.save(chatting);
            return ;
        }
        Chatting chatting = new Chatting(chattingCommentRequestDto.getComment(), chatBoard, chatBoard.getTarget(), chatBoard.getHost());
        chattingRepository.save(chatting);
    }

    @Transactional(readOnly = true)
    public List<ChattingCommentResponseDto> getMyChattingList(long id){
        List<Chatting> chatting = chattingRepository.findAllByChatBoard_Id(id).orElseThrow(() -> {
            throw new NotFoundChattingException("채팅방이 존재하지 않습니다.");
        });
        List<ChattingCommentResponseDto> chattingCommentList = new LinkedList<>();
        chatting.forEach(chat -> chattingCommentList.add(new ChattingCommentResponseDto().toDo(chat)));
        return chattingCommentList;
    }

    @Transactional
    public void chatDelete(long id){
        Chatting chatting = chattingRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundChatBoardException();
        });
        if(chatting.getSender().equals(authentication.getName())) {
            chatting.setDoDelete("true");
            return ;
        }
        throw new NotMatchSenderDeleteException();
    }
}
