package com.example.hospitalproject.Service.Chat;

import com.example.hospitalproject.Dto.ChatBoard.ChatBoardListResponseDto;
import com.example.hospitalproject.Dto.ChatBoard.ChatBoardReceiverRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.EditPrivateTitleRequestDto;
import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.ChatTitleType;
import com.example.hospitalproject.Entity.Chatting.Chatting;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import com.example.hospitalproject.Repository.ChatBoard.ChattingRepository;
import com.example.hospitalproject.Service.ChatBoard.ChatBoardService;
import com.example.hospitalproject.Service.ChatBoard.Response.ChatBoardTypeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.hospitalproject.Controller.Create.ControllerCreate.createUser;
import static com.example.hospitalproject.Service.Create.ServiceCreate.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChatBoardServiceTest {
    @InjectMocks
    private ChatBoardService chatBoardService;
    @Mock
    private ChatBoardRepository chatBoardRepository;
    @Mock // verify() -> Mock 어노테이션만 검증할 수 있음
    private ChatBoardTypeResponse chatBoardTypeResponse;
    @Mock
    private ChattingRepository chattingRepository;

    /**
     * 서비스 센터 (고객센터) 채팅방
     * */
    @DisplayName("채팅방(id)에서 채팅하기")
    @Test
    void createServiceCenterChatBoardTest() {
        // given
        User user = createUser();

        // when
        chatBoardService.createServiceCenterChatBoard(user);

        // then
        verify(chatBoardRepository).save(any());
        verify(chatBoardTypeResponse).createChatBoardServiceInit(user.getUsername(), ChatTitleType.SERVICE_CENTER.getValue());
    }

    /**
     * 개인 채팅방 (1:1)
     */
    @DisplayName("개인 채팅방 (1:1)")
    @Test
    void createPrivateChatBoardTest() {
        // given
        ChatBoardReceiverRequestDto chatBoardReceiverRequestDto = createChatBoardReceiverDto();
        User user = createUser();

        // when
        chatBoardService.createPrivateChatBoard(chatBoardReceiverRequestDto, user);

        // then
        verify(chatBoardRepository).save(any());
        verify(chatBoardTypeResponse).createChatBoardPrivateInit(user.getUsername(), chatBoardReceiverRequestDto.getReceiver());
    }

    /**
     * 개인 채팅방 제목 변경
     */
    @DisplayName("개인 채팅방 제목 변경")
    @Test
    void editPrivateTitleTest() {
        // given
        Long id = 1L;
        User user = createUser();
        EditPrivateTitleRequestDto editPrivateTitleRequestDto = createEditPrivateTitleDto();
        ChatBoard chatBoard = createChatBoard();

        given(chatBoardRepository.findByIdAndHostOrTarget(id, user.getUsername(), user.getUsername())).willReturn(Optional.of(chatBoard));

        // when
        chatBoardService.editPrivateTitle(editPrivateTitleRequestDto, id, user);

        // then
        assertThat(chatBoard.getTitle()).isEqualTo(editPrivateTitleRequestDto.getChangeTitle());
    }

    /**
     * 채팅방 목록 가져오기
     */
    @DisplayName("채팅방 목록 가져오기")
    @Test
    void getMyChatBoardListTest() {
        // given
        User user = createUser();
        ChatBoard chatBoard = createChatBoard();
        chatBoard.setId(0L);

        given(chatBoardRepository.findAllByHostOrTarget(user.getUsername(), user.getUsername())).willReturn(Optional.of(Collections.singletonList(chatBoard)));

        // when
        List<ChatBoardListResponseDto> chatBoardListResponseDto = chatBoardService.getMyChatBoardList(user);

        // then
        assertThat(chatBoardListResponseDto.get(0).getId()).isEqualTo(0L);
        assertThat(chatBoardListResponseDto.get(0).getChatTitle()).isEqualTo("테스트 채팅방");
        assertThat(chatBoardListResponseDto.get(0).getHost()).isEqualTo("Tester");
        assertThat(chatBoardListResponseDto.get(0).getTarget()).isEqualTo("Tester1");
        assertThat(chatBoardListResponseDto.get(0).getChattingType()).isEqualTo(ChatTitleType.PRIVATE_CHAT.name());
    }

    /**
     * 채팅방 삭제 -> 진짜로 삭제되는 것은 아니고, 데이터 보관을 위해 true, false로 여부만 확인한다.
     */
    @DisplayName("채팅방 삭제")
    @Test
    void deleteChatBoardTest() {
        // given
        Long id = 1L;
        User user = createUser();
        ChatBoard chatBoard = createChatBoard();
        Chatting chatting = createChatting();
        chatBoard.setId(1L);
        chatBoard.setHostDelete("true");
        chatBoard.setTargetDelete("true");

        given(chatBoardRepository.findByIdAndHostOrTarget(id, user.getUsername(), user.getUsername())).willReturn(Optional.of(chatBoard));
        given(chattingRepository.findAllByChatBoard_IdAndHostOrTarget(chatBoard.getId(), user.getUsername(), user.getUsername())).willReturn(Optional.of(Collections.singletonList(chatting)));

        // when
        chatBoardService.deleteChatBoard(id, user);

        // then
        verify(chatBoardRepository).findByIdAndHostOrTarget(id, user.getUsername(), user.getUsername());
        verify(chattingRepository).findAllByChatBoard_IdAndHostOrTarget(id, user.getUsername(), user.getUsername());
        assertThat(chatBoard.getHostDelete()).isEqualTo("true");
        assertThat(chatBoard.getTargetDelete()).isEqualTo("true");
        assertThat(chatting.getDoDelete()).isEqualTo("true");
    }
}
