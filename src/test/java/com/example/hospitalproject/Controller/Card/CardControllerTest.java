package com.example.hospitalproject.Controller.Card;

import com.example.hospitalproject.Controller.Payment.Card.CardRestController;
import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Service.Payment.Card.CardService;
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

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CardControllerTest {
    @InjectMocks // Mock(가짜객체) 개체 주입
    private CardRestController cardRestController;
    @Mock // Mock 생성
    private CardService cardService;

    // Controller 테스트시 HTTP 호출이 필요한데, 일반적으론 불가능 하기 때문에 아래와 같은 변수와 함수로 생성해야 한다.
    private MockMvc mockMvc;

    @BeforeEach
    public void mockMvcInit(){
        mockMvc = MockMvcBuilders.standaloneSetup(cardRestController).build();
    }

    /**
     * 신용카드 등록
     */
    @DisplayName("신용카드 등록")
    @Test
    void cardRegistrationTest() throws Exception {
        // given
        CardInfoRequestDto cardInfoRequestDto = cardCreate();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/payment/card/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(cardInfoRequestDto))
        );

        // then
        resultActions.andExpect(status().isOk()).andReturn();
        verify(cardService).cardRegistration(cardInfoRequestDto);
    }

    /**
     * 등록한 카드 중 사용할 카드 선택하기 (단일)
     */
    @DisplayName("등록한 카드 중 사용할 카드 선택하기")
    @Test
    void cardChoice() throws Exception {
        // given
        long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/payment/card/choice/"+id)
        );

        // then
        resultActions.andExpect(status().isOk()).andReturn();
    }

    /**
     * 등록한 카드 리스트 조회
     */
    @DisplayName("등록한 카드 리스트 조회")
    @Test
    void getMyCardList() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/payment/card/my/list")
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 현재 선택으로 되어 있는 카드 조회
     */
    @DisplayName("현재 선택으로 되어 있는 카드 조회")
    @Test
    void getMyCard() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/payment/card/my")
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 선택한 카드 변경 (이전 카드와 변경될 카드)
     */
    @DisplayName("선택한 카드 변경")
    @Test
    void changeMyChoiceCard() throws Exception {
        // given
        long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/payment/card/my/"+id)
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 하나의 카드가 등록되어 있고 미선택으로 바꾸고 싶은 경우
     */
    @DisplayName("하나의 카드가 등록되어 있고 미선택으로 바꾸고 싶은 경우")
    @Test
    void cardChoiceChange() throws Exception {
        // given
        long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/payment/card/status/"+id)
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 등록한 카드 삭제
     */
    @DisplayName("등록한 카드 삭제")
    @Test
    void deleteRegistrationCard() throws Exception {
        // given
        long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/payment/card/"+id)
        );
        // then
        resultActions.andExpect(status().isOk());
    }

    private CardInfoRequestDto cardCreate(){
        return CardInfoRequestDto.builder()
                .bank("국민은행")
                .cardNumber("1234-1234-1234-1234")
                .validYear("24")
                .validMonth("20")
                .password("12")
                .selectCard("선택")
                .build();
    }
}
