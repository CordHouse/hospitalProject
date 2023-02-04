package com.example.hospitalproject.Service.Card;

import com.example.hospitalproject.Repository.Payment.Card.CardRepository;
import com.example.hospitalproject.Service.Payment.Card.CardService;
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
public class CardServiceTest {
    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void mockMvcInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(cardService).build();
    }

    /**
     * 카드 등록하기
     */
    @DisplayName("카드 등록하기")
    @Test
    void cardRegistration() {

    }

    /**
     * 등록된 카드 중 선택 할 수 있도록 기능 추가
     */
    @DisplayName("등록된 카드 중 선택 할 수 있도록 기능 추가")
    @Test
    void cardChoice() {

    }

    /**
     * 등록한 카드 리스트 조회
     */
    @DisplayName("등록한 카드 리스트 조회")
    @Test
    void getMyCardList() {

    }

    /**
     * 사용할 카드로 선택한 카드 조회
     */
    @DisplayName("사용할 카드로 선택한 카드 조회")
    @Test
    void getMyCard() {

    }

    /**
     * 사용할 카드 변경
     */
    @DisplayName("사용할 카드 변경")
    @Test
    void changeMyChoiceCard() {

    }

    /**
     * 등록한 카드가 하나이고 선택상태를 미선택으로 바꾸고 싶은 경우
     */
    @DisplayName("등록한 카드가 하나이고 선택상태를 미선택으로 바꾸고 싶은 경우")
    @Test
    void cardChoiceChange() {

    }

    /**
     * 등록된 카드 삭제
     */
    @DisplayName("등록된 카드 삭제")
    @Test
    void deleteRegistrationCard() {

    }
}
