package com.example.hospitalproject.Service.Card;

import com.example.hospitalproject.Authentication.UsernameValid;
import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Entity.Payment.Credit.Card;
import com.example.hospitalproject.Repository.Payment.Card.CardRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Service.Payment.Card.CardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.hospitalproject.Service.Create.ServiceCreate.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UsernameValid usernameValid;

    /**
     * 카드 등록하기
     * 여기 수정해야함
     */
    @DisplayName("카드 등록하기")
    @Test
    void cardRegistration() {
        // given
        CardInfoRequestDto cardInfoRequestDto = createCardInfo();
        String cardNumberTestTrue = "1234-1234-1234-1234";
        String cardNumberTestFalse = "2345-1234-1234-1234";

        // when
        usernameValid.doAuthenticationUsernameCheck();
        cardService.cardRegistration(cardInfoRequestDto);

        // then
        assertThat(cardInfoRequestDto.getBank()).isEqualTo("국민은행");
        assertThat(cardInfoRequestDto.getCardNumber()).isEqualTo(cardNumberTestTrue);
        assertThat(cardInfoRequestDto.getCardNumber()).isNotEqualTo(cardNumberTestFalse);
    }

    /**
     * 등록된 카드 중 선택 할 수 있도록 기능 추가
     */
    @DisplayName("등록된 카드 중 선택 할 수 있도록 기능 추가")
    @Test
    void cardChoice() {
        // given
        Long id = 1L;
        Card card = createCardInit();

        // when
        authentication();
        cardRepository.findById(id);

        // then
        assertThat(card.getSelectCard()).isEqualTo("선택");
        assertThat(card.getSelectCard()).isNotEqualTo("미선택");
        verify(cardRepository).findById(id);
    }

    /**
     * 등록한 카드 리스트 조회
     */
    @DisplayName("등록한 카드 리스트 조회")
    @Test
    void getMyCardList() {
        // given
        List<Card> card = List.of(createCardInit());

        // then
        assertThat(card.get(0)).isEqualTo(createCardInit());
    }

    /**
     * 사용할 카드로 선택한 카드 조회
     */
    @DisplayName("사용할 카드로 선택한 카드 조회")
    @Test
    void getMyCard() {
        // given
        List<Card> card = List.of(createCardInit());

        // then
        assertThat(card.get(0).getSelectCard()).isEqualTo("선택");
        assertThat(card.get(0).getSelectCard()).isNotEqualTo("미선택");
    }

    /**
     * 사용할 카드 변경
     */
    @DisplayName("사용할 카드 변경")
    @Test
    void changeMyChoiceCard() {
        // given
        usernameValid.doAuthenticationUsernameCheck();
        Long id = 1L;
        Card cardBefore = createCardInit();
        Card cardAfter = createCardInit();

        // when
        cardRepository.findById(id);

        // then
        assertThat(cardBefore.getUser().getUsername()).isEqualTo("jiwoo");
        assertThat(cardAfter.getUser().getUsername()).isEqualTo("jiwoo");
        verify(cardRepository).findById(id);
    }

    /**
     * 등록한 카드가 하나이고 선택상태를 미선택으로 바꾸고 싶은 경우
     */
    @DisplayName("등록한 카드가 하나이고 선택상태를 미선택으로 바꾸고 싶은 경우")
    @Test
    void cardChoiceChange() {
        // given
        Long id = 1L;
        Card card = createCardInit();

        // when
        cardRepository.findByIdAndUser_Username(id, authentication().getName());
        card.setSelectCard("미선택");

        // then
        assertThat(card.getSelectCard()).isEqualTo("미선택");
        assertThat(card.getSelectCard()).isNotEqualTo("선택");
        verify(cardRepository).findByIdAndUser_Username(id, authentication().getName());
    }

    /**
     * 등록된 카드 삭제
     */
    @DisplayName("등록된 카드 삭제")
    @Test
    void deleteRegistrationCard() {
        // given
        Long id = 1L;

        // when
        authentication();
        cardRepository.deleteById(id);

        // then
        verify(cardRepository).deleteById(id);
    }
}
