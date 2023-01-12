package com.example.hospitalproject.Service.ChatBoard;

import com.example.hospitalproject.Dto.ChatBoard.ChatBoardReceiverRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.EditPrivateTitleRequestDto;
import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.ChatTitleType;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChatBoardException;
import com.example.hospitalproject.Exception.UserException.NotFoundUsernameException;
import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatBoardService{
    private final ChatBoardRepository chatBoardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createServiceCenterChatBoard(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ChatBoard chatBoard = new ChatBoard(
                ChatTitleType.SERVICE_CENTER.getValue(),
                ChatTitleType.SERVICE_CENTER,
                ChatTitleType.SERVICE_CENTER.getValue(),
                authentication.getName());

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
                chatBoardReceiverRequestDto.getReceiver(),
                authentication.getName());
        chatBoardRepository.save(chatBoard);
    }

    @Transactional
    public void editPrivateTitle(EditPrivateTitleRequestDto editPrivateTitleRequestDto, long id){
        ChatBoard chatBoard = chatBoardRepository.findById(id).orElseThrow(NotFoundChatBoardException::new);
        chatBoard.setTitle(editPrivateTitleRequestDto.getChangeTitle());
    }

    @Transactional
    public void deleteChatBoard(long id){
        ChatBoard chatBoard = chatBoardRepository.findById(id).orElseThrow(NotFoundChatBoardException::new);
        chatBoard.setDelete("true");
    }
}
