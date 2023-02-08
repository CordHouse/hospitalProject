package com.example.hospitalproject.Service.Card;

import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Entity.Payment.Credit.Card;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Repository.Payment.Card.CardRepository;
import com.example.hospitalproject.Service.Payment.Card.CardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.hospitalproject.Controller.Create.ControllerCreate.createUser;
import static com.example.hospitalproject.Service.Create.ServiceCreate.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    /**
     * 카드 등록하기
     */
    @DisplayName("카드 등록하기")
    @Test
    void cardRegistrationTest() {
        // given
        CardInfoRequestDto cardInfoRequestDto = createCardInfo();
        String cardNumberTestTrue = "1234-1234-1234-1234";
        String cardNumberTestFalse = "2345-1234-1234-1234";
        User user = createUser();

        // when
        cardService.cardRegistration(cardInfoRequestDto, user);

        // then
        assertThat(cardInfoRequestDto.getBank()).isEqualTo("국민은행");
        assertThat(cardInfoRequestDto.getCardNumber()).isEqualTo(cardNumberTestTrue);
        assertThat(cardInfoRequestDto.getCardNumber()).isNotEqualTo(cardNumberTestFalse);
        verify(cardRepository).save(any()); // any() -> 어떤 값이 들어가도 정상 실행되어야 한다.
    }

    /**
     * 등록된 카드 중 선택 할 수 있도록 기능 추가
     */
    @DisplayName("등록된 카드 중 선택 할 수 있도록 기능 추가")
    @Test
    void cardChoiceTest() {
        // given
        long id = 1L;
        Card card = createCardInit();
        card.setSelectCard("미선택");
        User user = createUser();
        given(cardRepository.findByIdAndUser_Username(id, user.getUsername())).willReturn(Optional.of(card));
        given(cardRepository.findAllByUser(user)).willReturn(Collections.singletonList(card));

        // when
        cardService.cardChoice(id, user);

        // then
        assertThat(card.getSelectCard()).isEqualTo("선택");
        assertThat(card.getSelectCard()).isNotEqualTo("미선택");
        verify(cardRepository).findByIdAndUser_Username(id, user.getUsername());
    }

    /**
     * 등록한 카드 리스트 조회
     */
    @DisplayName("등록한 카드 리스트 조회")
    @Test
    void getMyCardListTest() {
        // given
        List<Card> card = List.of(createCardInit());
        User user = createUser();
        given(cardRepository.findAllByUser(user)).willReturn(card);

        // when
        cardService.getMyCardList(user);

        // then
        assertThat(card.get(0)).isEqualTo(createCardInit());
        verify(cardRepository).findAllByUser(user);
    }

    /**
     * 사용할 카드로 선택한 카드 조회
     */
    @DisplayName("사용할 카드로 선택한 카드 조회")
    @Test
    void getMyCardTest() {
        // given
        List<Card> card = List.of(createCardInit());
        User user = createUser();
        given(cardRepository.findAllByUser(user)).willReturn(card);

        // when
        cardService.getMyCard(user);

        // then
        assertThat(card.get(0).getSelectCard()).isEqualTo("선택");
        assertThat(card.get(0).getSelectCard()).isNotEqualTo("미선택");
        verify(cardRepository).findAllByUser(user);
    }

    /**
     * 사용할 카드 변경
     */
    @DisplayName("사용할 카드 변경")
    @Test
    void changeMyChoiceCardTest() {
        // given
        Long id = 1L;
        Card cardBefore = createCardInit();
        cardBefore.setId(2L);

        Card cardAfter = createCardInit();
        User user = createUser();

        System.out.println(cardBefore);
        System.out.println(cardAfter);

        given(cardRepository.findAllByUser(user)).willReturn(Collections.singletonList(cardBefore));
        given(cardRepository.findByIdAndUser_Username(cardBefore.getId(), user.getUsername())).willReturn(Optional.of(cardBefore));
        given(cardRepository.findByIdAndUser_Username(id, user.getUsername())).willReturn(Optional.of(cardAfter));

        // when
        cardService.changeMyChoiceCard(id, user);

        // then
        assertThat(cardBefore.getUser().getUsername()).isEqualTo("Tester");
        assertThat(cardAfter.getUser().getUsername()).isEqualTo("Tester");
        verify(cardRepository).findByIdAndUser_Username(cardBefore.getId(), user.getUsername());
        verify(cardRepository).findByIdAndUser_Username(id, user.getUsername());
    }

    /**
     * 등록한 카드가 하나이고 선택상태를 미선택으로 바꾸고 싶은 경우
     */
    @DisplayName("등록한 카드가 하나이고 선택상태를 미선택으로 바꾸고 싶은 경우")
    @Test
    void cardChoiceChangeTest() {
        // given
        Long id = 1L;
        Card card = createCardInit();
        User user = createUser();
        given(cardRepository.findByIdAndUser_Username(id, user.getUsername())).willReturn(Optional.of(card));

        // when
        cardService.cardChoiceChange(id, user);
        card.setSelectCard("미선택");

        // then
        assertThat(card.getSelectCard()).isEqualTo("미선택");
        assertThat(card.getSelectCard()).isNotEqualTo("선택");
        verify(cardRepository).findByIdAndUser_Username(id, user.getUsername());
    }

    /**
     * 등록된 카드 삭제
     */
    @DisplayName("등록된 카드 삭제")
    @Test
    void deleteRegistrationCardTest() {
        // given
        Long id = 1L;
        User user = createUser();
        Card card = createCardInit();
        given(cardRepository.findByIdAndUser_Username(id, user.getUsername())).willReturn(Optional.of(card));

        // when
        cardService.deleteRegistrationCard(id, user);

        // then
        verify(cardRepository).findByIdAndUser_Username(id, user.getUsername());
        verify(cardRepository).deleteById(id);
    }
}
