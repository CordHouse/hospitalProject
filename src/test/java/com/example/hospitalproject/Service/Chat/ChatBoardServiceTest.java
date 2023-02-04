package com.example.hospitalproject.Service.Chat;

import com.example.hospitalproject.Repository.ChatBoard.ChatBoardRepository;
import com.example.hospitalproject.Service.ChatBoard.ChatBoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ChatBoardServiceTest {
    @InjectMocks
    private ChatBoardService chatBoardService;
    @Mock
    private ChatBoardRepository chatBoardRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void mockMvcInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(chatBoardService).build();
    }

    /**
     * 서비스 센터 (고객센터) 채팅방
     * */
    @DisplayName("채팅방(id)에서 채팅하기")
    @Test
    void createServiceCenterChatBoard() {

    }

    /**
     * 개인 채팅방 (1:1)
     */
    @DisplayName("개인 채팅방 (1:1)")
    @Test
    void createPrivateChatBoard() {

    }

    /**
     * 개인 채팅방 제목 변경
     */
    @DisplayName("개인 채팅방 제목 변경")
    @Test
    void editPrivateTitle() {

    }

    /**
     * 채팅방 목록 가져오기
     */
    @DisplayName("채팅방 목록 가져오기")
    @Test
    void getMyChatBoardList() {

    }

    /**
     * 채팅방 삭제 -> 진짜로 삭제되는 것은 아니고, 데이터 보관을 위해 true, false로 여부만 확인한다.
     */
    @DisplayName("채팅방 삭제")
    @Test
    void deleteChatBoard() {

    }
}
