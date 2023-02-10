package com.example.hospitalproject.Service.ChatBoard;

import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentResponseDto;
import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.Chatting;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChatBoardException;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChattingException;
import com.example.hospitalproject.Exception.ChatBoard.NotMatchSenderDeleteException;
import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import com.example.hospitalproject.Repository.ChatBoard.ChattingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final ChattingRepository chattingRepository;
    private final ChatBoardRepository chatBoardRepository;

    /**
     * 채팅방(id)에서 채팅하기
     */
    @Transactional
    public void chatRunStatus(Long id, ChattingCommentRequestDto chattingCommentRequestDto, User user){
        ChatBoard chatBoard = chatBoardRepository.findByIdAndHostOrTarget(id, user.getUsername(), user.getUsername()).orElseThrow(() -> {
            throw new NotFoundChatBoardException();
        });
        if(user.getUsername().equals(chatBoard.getHost())) {
            chattingRepository.save(new Chatting(chattingCommentRequestDto.getComment(), chatBoard, chatBoard.getHost(), chatBoard.getTarget()));
            return ;
        }
        chattingRepository.save(new Chatting(chattingCommentRequestDto.getComment(), chatBoard, chatBoard.getTarget(), chatBoard.getHost()));
    }

    /**
     * 채팅방 내부 채팅 조회
     */
    @Transactional(readOnly = true)
    public List<ChattingCommentResponseDto> getMyChattingList(long id, User user){
        List<Chatting> chatting = chattingRepository.findAllByChatBoard_IdAndHostOrTarget(id, user.getUsername(), user.getUsername()).orElseThrow(() -> {
            throw new NotFoundChattingException("채팅방이 존재하지 않습니다.");
        });
        List<ChattingCommentResponseDto> chattingCommentList = new LinkedList<>();
        chatting.forEach(chat -> chattingCommentList.add(new ChattingCommentResponseDto().toDo(chat)));
        return chattingCommentList;
    }

    /**
     * 채팅 삭제
     */
    @Transactional
    public void chatDelete(long id, User user){
        Chatting chatting = chattingRepository.findByIdAndHost(id, user.getUsername()).orElseThrow(() -> {
            throw new NotFoundChatBoardException();
        });
        if(chatting.getHost().equals(user.getUsername())) {
            chatting.setDoDelete("true");
            return ;
        }
        throw new NotMatchSenderDeleteException();
    }
}
