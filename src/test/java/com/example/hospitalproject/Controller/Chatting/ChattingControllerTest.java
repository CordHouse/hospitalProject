package com.example.hospitalproject.Controller.Chatting;

import com.example.hospitalproject.Controller.ChatBoard.ChattingRestController;
import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentRequestDto;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Service.ChatBoard.ChattingService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.example.hospitalproject.Controller.Create.ControllerCreate.createToken;
import static com.example.hospitalproject.Controller.Create.ControllerCreate.createUser;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ChattingControllerTest {
    @InjectMocks // Mock(가짜객체) 개체 주입
    private ChattingRestController chattingRestController;
    @Mock // Mock 생성
    private ChattingService chattingService;

    @Mock
    private UserRepository userRepository;

    // Controller 테스트시 HTTP 호출이 필요한데, 일반적으론 불가능 하기 때문에 아래와 같은 변수와 함수로 생성해야 한다.
    private MockMvc mockMvc;

    @BeforeEach
    public void mockMvcInit(){
        mockMvc = MockMvcBuilders.standaloneSetup(chattingRestController).build();
    }

    /**
     * 채팅방(id)에서 채팅하기
     */
    @DisplayName("채팅방에서 채팅하기")
    @Test
    void chatRunStatusTest() throws Exception {
        // given
        Long id = 1L;
        ChattingCommentRequestDto chattingCommentRequestDto = new ChattingCommentRequestDto("Test 반갑다");
        User user = createUser();
        testUserValidSet(user);

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.post("/chatting/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(chattingCommentRequestDto))
        ).andExpect(status().isOk());

        // then
        verify(chattingService).chatRunStatus(id, chattingCommentRequestDto, user);
    }

    /**
     * 채팅방 안의 채팅 조회
     */
    @DisplayName("채팅방 안에서 채팅 조회")
    @Test
    void getMyChattingListTest() throws Exception {
        // given
        Long id = 1L;
        User user = createUser();
        testUserValidSet(user);

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.get("/chatting/"+id+"/comment")
        ).andExpect(status().isOk());

        // then
        verify(chattingService).getMyChattingList(id, user);
    }

    /**
     * 채팅 삭제
     */
    @DisplayName("채팅 삭제")
    @Test
    void chatDeleteTest() throws Exception {
        // given
        Long id = 1L;
        User user = createUser();
        testUserValidSet(user);

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/chatting/"+id)
        ).andExpect(status().isOk());

        // then
        verify(chattingService).chatDelete(id, user);
    }

    void testUserValidSet(User user) {
        Authentication authentication = createToken();
        given(userRepository.findByUsername(authentication.getName())).willReturn(Optional.of(user));
    }
}
