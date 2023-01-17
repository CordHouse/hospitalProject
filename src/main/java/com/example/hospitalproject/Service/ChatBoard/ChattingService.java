package com.example.hospitalproject.Service.ChatBoard;

import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.ChattingListResponseDto;
import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.Chatting;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChatBoardException;
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

    @Transactional
    public void chatRunStatus(Long id, ChattingCommentRequestDto chattingCommentRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
    public List<ChattingListResponseDto> getMyChattingList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<ChatBoard> chatBoards = chatBoardRepository.findAllByHostOrTarget(authentication.getName(), authentication.getName()).orElseThrow(() -> {
            throw new NotFoundChatBoardException();
        });
        List<ChattingListResponseDto> myChattingList = new LinkedList<>();
        chatBoards.forEach(board -> myChattingList.add(new ChattingListResponseDto().toDo(board)));
        return myChattingList;
    }

    @Transactional
    public void chatDelete(long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
