package com.example.hospitalproject.Controller.Chat;

import com.example.hospitalproject.Controller.ChatBoard.ChatBoardRestController;
import com.example.hospitalproject.Service.ChatBoard.ChatBoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {
    @InjectMocks // Mock(가짜객체) 개체 주입
    private ChatBoardRestController chatBoardRestController;
    @Mock // Mock 생성
    private ChatBoardService chatBoardService;

    // Controller 테스트시 HTTP 호출이 필요한데, 일반적으론 불가능 하기 때문에 아래와 같은 변수와 함수로 생성해야 한다.
    private MockMvc mockMvc;

    @BeforeEach
    public void mockMvcInit(){
        mockMvc = MockMvcBuilders.standaloneSetup(chatBoardRestController).build();
    }

    /**
     * 서비스 센터 (고객센터) 채팅방
     */

    /**
     * 개인 채팅방 (1:1)
     */

    /**
     * 개인 채팅방 제목 변경
     */

    /**
     * 채팅방 목록 가져오기
     */

    /**
     * 채팅방 삭제 -> 진짜로 삭제되는 것은 아니고, 데이터 보관을 위해 true, false로 여부만 확인한다.
     */
}
