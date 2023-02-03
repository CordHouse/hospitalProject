package com.example.hospitalproject.Service.ChatBoard;

import com.example.hospitalproject.Authentication.UsernameValid;
import com.example.hospitalproject.Dto.ChatBoard.ChatBoardReceiverRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.ChatBoardListResponseDto;
import com.example.hospitalproject.Dto.ChatBoard.EditPrivateTitleRequestDto;
import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.ChatTitleType;
import com.example.hospitalproject.Entity.Chatting.Chatting;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChatBoardException;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChattingException;
import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import com.example.hospitalproject.Repository.ChatBoard.ChattingRepository;
import com.example.hospitalproject.Service.ChatBoard.Response.ChatBoardTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatBoardService{
    private final ChatBoardRepository chatBoardRepository;
    private final ChattingRepository chattingRepository;
    private final UsernameValid usernameValid;
    private final ChatBoardTypeResponse chatBoardTypeResponse;

    @Transactional
    public void createServiceCenterChatBoard(){
        Authentication authentication = usernameValid.doAuthenticationUsernameCheck();
        chatBoardRepository.save(
                chatBoardTypeResponse.createChatBoardServiceInit(authentication.getName(), ChatTitleType.SERVICE_CENTER.getValue())
        );
    }

    @Transactional
    public void createPrivateChatBoard(ChatBoardReceiverRequestDto chatBoardReceiverRequestDto){
        Authentication authentication = usernameValid.doAuthenticationUsernameCheck();
        chatBoardRepository.save(
                chatBoardTypeResponse.createChatBoardPrivateInit(authentication.getName(), chatBoardReceiverRequestDto.getReceiver())
        );
    }

    @Transactional
    public void editPrivateTitle(EditPrivateTitleRequestDto editPrivateTitleRequestDto, long id){
        usernameValid.doAuthenticationUsernameCheck();
        ChatBoard chatBoard = chatBoardRepository.findById(id).orElseThrow(NotFoundChatBoardException::new);
        chatBoard.setTitle(editPrivateTitleRequestDto.getChangeTitle());
    }

    @Transactional(readOnly = true)
    public List<ChatBoardListResponseDto> getMyChatBoardList(){
        Authentication authentication = usernameValid.doAuthenticationUsernameCheck();
        List<ChatBoard> chatBoards = chatBoardRepository.findAllByHostOrTarget(authentication.getName(), authentication.getName()).orElseThrow(() -> {
            throw new NotFoundChatBoardException();
        });
        List<ChatBoardListResponseDto> myChatBoardList = new LinkedList<>();
        chatBoards.forEach(board -> myChatBoardList.add(new ChatBoardListResponseDto().toDo(board)));
        return myChatBoardList;
    }

    @Transactional
    public void deleteChatBoard(long id){
        Authentication authentication = usernameValid.doAuthenticationUsernameCheck();
        ChatBoard chatBoard = chatBoardRepository.findById(id).orElseThrow(NotFoundChatBoardException::new);
        whoIsDelete(chatBoard, authentication.getName());
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
