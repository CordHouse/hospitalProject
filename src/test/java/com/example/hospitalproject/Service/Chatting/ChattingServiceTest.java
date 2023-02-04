package com.example.hospitalproject.Service.Chatting;

import com.example.hospitalproject.Repository.ChatBoard.ChattingRepository;
import com.example.hospitalproject.Service.ChatBoard.ChattingService;
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
public class ChattingServiceTest {
    @InjectMocks
    private ChattingService chattingService;
    @Mock
    private ChattingRepository chattingRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void mockMvcInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(chattingService).build();
    }

    /**
     * 채팅방(id)에서 채팅하기
     */
    @DisplayName("채팅방(id)에서 채팅하기")
    @Test
    void chatRunStatus() {

    }

    /**
     * 채팅방 내부 채팅 조회
     */
    @DisplayName("채팅방 내부 채팅 조회")
    @Test
    void getMyChattingList() {

    }

    /**
     * 채팅 삭제
     */
    @DisplayName("채팅 삭제")
    @Test
    void chatDelete() {

    }
}
