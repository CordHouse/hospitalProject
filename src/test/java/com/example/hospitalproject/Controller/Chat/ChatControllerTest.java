package com.example.hospitalproject.Controller.Chat;

import com.example.hospitalproject.Controller.ChatBoard.ChatBoardRestController;
import com.example.hospitalproject.Dto.ChatBoard.ChatBoardReceiverRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.EditPrivateTitleRequestDto;
import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import com.example.hospitalproject.Service.ChatBoard.ChatBoardService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {
    @InjectMocks // Mock(가짜객체) 개체 주입
    private ChatBoardRestController chatBoardRestController;
    @Mock // Mock 생성
    private ChatBoardService chatBoardService;

    @Mock
    private ChatBoardRepository chatBoardRepository;

    // Controller 테스트시 HTTP 호출이 필요한데, 일반적으론 불가능 하기 때문에 아래와 같은 변수와 함수로 생성해야 한다.
    private MockMvc mockMvc;

    @BeforeEach
    public void mockMvcInit(){
        mockMvc = MockMvcBuilders.standaloneSetup(chatBoardRestController).build();
    }

    /**
     * 서비스 센터 (고객센터) 채팅방
     */
    @DisplayName("고객센터 채팅방 생성")
    @Test
    void createChatBoard() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/chat/create/service/center")
        );

        // then
        resultActions.andExpect(status().isOk());
        verify(chatBoardService).createServiceCenterChatBoard();
    }

    /**
     * 개인 채팅방 (1:1)
     */
    @DisplayName("개인 채팅방 생성")
    @Test
    void createPrivateChatBoard() throws Exception {
        // given
        ChatBoardReceiverRequestDto chatBoardReceiverRequestDto = new ChatBoardReceiverRequestDto("채팅 테스트");

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/chat/create/private")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(chatBoardReceiverRequestDto))
        );

        // then
        resultActions.andExpect(status().isOk());
        verify(chatBoardService).createPrivateChatBoard(chatBoardReceiverRequestDto);
    }

    /**
     * 개인 채팅방 제목 변경
     */
    @DisplayName("개인 채팅방 제목 변경")
    @Test
    void editPrivateTitle() throws Exception {
        // given
        Long id = 1L;
        EditPrivateTitleRequestDto editPrivateTitleRequestDto = new EditPrivateTitleRequestDto("제목 변경");

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/chat/change/title/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(editPrivateTitleRequestDto))
        );

        // then
        resultActions.andExpect(status().isOk());
        verify(chatBoardService).editPrivateTitle(editPrivateTitleRequestDto, id);
    }

    /**
     * 채팅방 목록 가져오기
     */
    @DisplayName("채팅방 목록 가져오기")
    @Test
    void getMyChatBoardList() throws Exception {
        // given
        when(chatBoardService.getMyChatBoardList()).thenReturn(List.of());

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/chat/my")
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 채팅방 삭제 -> 진짜로 삭제되는 것은 아니고, 데이터 보관을 위해 true, false로 여부만 확인한다.
     */
    @DisplayName("채팅방 삭제")
    @Test
    void deleteChatBoard() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/chat/"+id)
        );

        // then
        resultActions.andExpect(status().isOk());
    }
}
