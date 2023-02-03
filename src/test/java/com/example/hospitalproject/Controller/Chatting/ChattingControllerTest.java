package com.example.hospitalproject.Controller.Chatting;

import com.example.hospitalproject.Controller.ChatBoard.ChattingRestController;
import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentRequestDto;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ChattingControllerTest {
    @InjectMocks // Mock(가짜객체) 개체 주입
    private ChattingRestController chattingRestController;
    @Mock // Mock 생성
    private ChattingService chattingService;

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
    void chatRunStatus() throws Exception {
        // given
        Long id = 1L;
        ChattingCommentRequestDto chattingCommentRequestDto = new ChattingCommentRequestDto("Test 반갑다");

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/chatting/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(chattingCommentRequestDto))
        );

        // then
        resultActions.andExpect(status().isOk());
        assertThat(chattingCommentRequestDto.getComment(), is("Test 반갑다"));
    }

    /**
     * 채팅방 안의 채팅 조회
     */
    @DisplayName("채팅방 안에서 채팅 조회")
    @Test
    void getMyChattingList() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/chatting/"+id+"/comment")
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 채팅 삭제
     */
    @DisplayName("채팅 삭제")
    @Test
    void chatDelete() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/chatting/"+id)
        );

        // then
        resultActions.andExpect(status().isOk());
    }
}
