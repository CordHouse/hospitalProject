package com.example.hospitalproject.Service.ChatBoard;

import com.example.hospitalproject.Dto.ChatBoard.ChatBoardReceiverRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.EditPrivateTitleRequestDto;
import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.ChatTitleType;
import com.example.hospitalproject.Entity.Chatting.Chatting;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChatBoardException;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChattingException;
import com.example.hospitalproject.Exception.UserException.NotFoundUsernameException;
import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import com.example.hospitalproject.Repository.ChatBoard.ChattingRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatBoardService{
    private final ChatBoardRepository chatBoardRepository;
    private final ChattingRepository chattingRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createServiceCenterChatBoard(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ChatBoard chatBoard = new ChatBoard(
                ChatTitleType.SERVICE_CENTER.getValue(),
                ChatTitleType.SERVICE_CENTER,
                authentication.getName(),
                ChatTitleType.SERVICE_CENTER.getValue());

        chatBoardRepository.save(chatBoard);
    }

    @Transactional
    public void createPrivateChatBoard(ChatBoardReceiverRequestDto chatBoardReceiverRequestDto){
        userRepository.findByUsername(chatBoardReceiverRequestDto.getReceiver()).orElseThrow(() -> {
            throw new NotFoundUsernameException("해당 아이디는 회원 계정이 아닙니다.");
        });
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ChatBoard chatBoard = new ChatBoard(
                ChatTitleType.PRIVATE_CHAT.name(),
                ChatTitleType.PRIVATE_CHAT,
                authentication.getName(),
                chatBoardReceiverRequestDto.getReceiver());
        chatBoardRepository.save(chatBoard);
    }

    @Transactional
    public void editPrivateTitle(EditPrivateTitleRequestDto editPrivateTitleRequestDto, long id){
        ChatBoard chatBoard = chatBoardRepository.findById(id).orElseThrow(NotFoundChatBoardException::new);
        chatBoard.setTitle(editPrivateTitleRequestDto.getChangeTitle());
    }

    @Transactional
    public void deleteChatBoard(long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
            throw new NotFoundUsernameException("유저정보가 올바르지 않습니다.");
        });
        ChatBoard chatBoard = chatBoardRepository.findById(id).orElseThrow(NotFoundChatBoardException::new);
        whoIsDelete(chatBoard, user.getUsername());
        hostAndTargetDoDeleteCheck(chatBoard);
    }

    @Transactional
    protected void whoIsDelete(ChatBoard chatBoard, String username){
        if(chatBoard.getHost().equals(username)){
            chatBoard.setHostDelete("true");
        }
        chatBoard.setTargetDelete("true");
    }

    @Transactional
    protected void hostAndTargetDoDeleteCheck(ChatBoard chatBoard){
        if(chatBoard.getHostDelete().equals("true") && chatBoard.getTargetDelete().equals("true")) {
            List<Chatting> chatting = chattingRepository.findAllByChatBoard_Id(chatBoard.getId()).orElseThrow(() -> {
                throw new NotFoundChattingException("채팅방에 채팅이 존재하지 않습니다.");
            });
            chatting.forEach(chat -> chat.setDoDelete("true"));
        }
    }
}
