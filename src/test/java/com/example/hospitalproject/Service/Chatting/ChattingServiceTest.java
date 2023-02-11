package com.example.hospitalproject.Service.Chatting;

import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentResponseDto;
import com.example.hospitalproject.Entity.Chatting.Chatting;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import com.example.hospitalproject.Repository.ChatBoard.ChattingRepository;
import com.example.hospitalproject.Service.ChatBoard.ChattingService;
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
public class ChattingServiceTest {
    @InjectMocks
    private ChattingService chattingService;
    @Mock
    private ChattingRepository chattingRepository;
    @Mock
    private ChatBoardRepository chatBoardRepository;


    /**
     * 채팅방(id)에서 채팅하기
     */
    @DisplayName("채팅방(id)에서 채팅하기")
    @Test
    void chatRunStatusTest() {
        // given
        Long id = 1L;
        ChattingCommentRequestDto chattingCommentRequestDto = createChattingCommentRequest();
        User user = createUser();

        given(chatBoardRepository.findByIdAndHostOrTarget(id, user.getUsername(), user.getUsername())).willReturn(Optional.of(createChatBoard()));

        // when
        chattingService.chatRunStatus(id, chattingCommentRequestDto, user);

        // then
        verify(chattingRepository).save(any());
    }

    /**
     * 채팅방 내부 채팅 조회
     */
    @DisplayName("채팅방 내부 채팅 조회")
    @Test
    void getMyChattingListTest() {
        // given
        Long id = 1L;
        User user = createUser();
        Chatting chatting = createChatting();
        chatting.setId(0L);
        chatting.getChatBoard().setId(0L);

        given(chattingRepository.findAllByChatBoard_IdAndHostOrTarget(id, user.getUsername(), user.getUsername())).willReturn(Optional.of(Collections.singletonList(chatting)));

        // when
        List<ChattingCommentResponseDto> chattingCommentResponseDto = chattingService.getMyChattingList(id, user);

        // then
        assertThat(chattingCommentResponseDto.get(0).getId()).isEqualTo(0);
        assertThat(chattingCommentResponseDto.get(0).getText()).isEqualTo("테스트 답변");
        assertThat(chattingCommentResponseDto.get(0).getHost()).isEqualTo("Tester");
        assertThat(chattingCommentResponseDto.get(0).getTarget()).isEqualTo("Tester1");
        assertThat(chattingCommentResponseDto.get(0).getChatBoardId()).isEqualTo(0);
    }

    /**
     * 채팅 삭제
     */
    @DisplayName("채팅 삭제")
    @Test
    void chatDeleteTest() {
        // given
        Long id = 1L;
        User user = createUser();
        Chatting chatting = createChatting();

        given(chattingRepository.findByIdAndHost(id, user.getUsername())).willReturn(Optional.of(chatting));

        // when
        chattingService.chatDelete(id, user);

        // then
        assertThat(chatting.getDoDelete()).isEqualTo("true");
    }
}
