package com.example.hospitalproject.Controller.Chat;

import com.example.hospitalproject.Controller.ChatBoard.ChatBoardRestController;
import com.example.hospitalproject.Dto.ChatBoard.ChatBoardReceiverRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.EditPrivateTitleRequestDto;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Service.ChatBoard.ChatBoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {
    @InjectMocks // Mock(가짜객체) 개체 주입
    private ChatBoardRestController chatBoardRestController;
    @Mock // Mock 생성
    private ChatBoardService chatBoardService;
    @Mock
    private UserRepository userRepository;

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
    void createChatBoardTest() throws Exception {
        // given
        User user = createUser();
        testUserValidSet(user);

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.post("/chat/create/service/center")
        ).andExpect(status().isOk());

        // then
        verify(chatBoardService).createServiceCenterChatBoard(user);
    }

    /**
     * 개인 채팅방 (1:1)
     */
    @DisplayName("개인 채팅방 생성")
    @Test
    void createPrivateChatBoardTest() throws Exception {
        // given
        ChatBoardReceiverRequestDto chatBoardReceiverRequestDto = new ChatBoardReceiverRequestDto("채팅 테스트");
        User user = createUser();
        testUserValidSet(user);

        // when
        mockMvc.perform(
                post("/chat/create/private")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(chatBoardReceiverRequestDto))
        ).andExpect(status().isOk());

        // then
        verify(chatBoardService).createPrivateChatBoard(chatBoardReceiverRequestDto, user);
    }

    /**
     * 개인 채팅방 제목 변경
     */
    @DisplayName("개인 채팅방 제목 변경")
    @Test
    void editPrivateTitleTest() throws Exception {
        // given
        Long id = 1L;
        EditPrivateTitleRequestDto editPrivateTitleRequestDto = new EditPrivateTitleRequestDto("제목 변경");
        User user = createUser();
        testUserValidSet(user);

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.put("/chat/change/title/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(editPrivateTitleRequestDto))
        ).andExpect(status().isOk());

        // then
        verify(chatBoardService).editPrivateTitle(editPrivateTitleRequestDto, id, user);
    }

    /**
     * 채팅방 목록 가져오기
     */
    @DisplayName("채팅방 목록 가져오기")
    @Test
    void getMyChatBoardListTest() throws Exception {
        // given
        User user = createUser();
        testUserValidSet(user);

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.get("/chat/my")
        ).andExpect(status().isOk());

        // then
        verify(chatBoardService).getMyChatBoardList(user);
    }

    /**
     * 채팅방 삭제 -> 진짜로 삭제되는 것은 아니고, 데이터 보관을 위해 true, false로 여부만 확인한다.
     */
    @DisplayName("채팅방 삭제")
    @Test
    void deleteChatBoardTest() throws Exception {
        // given
        Long id = 1L;
        User user = createUser();
        testUserValidSet(user);

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/chat/"+id)
        ).andExpect(status().isOk());

        // then
        verify(chatBoardService).deleteChatBoard(id, user);
    }

    void testUserValidSet(User user) {
        Authentication authentication = createToken();
        given(userRepository.findByUsername(authentication.getName())).willReturn(Optional.of(user));
    }
}
