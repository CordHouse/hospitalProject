package com.example.hospitalproject.Service.Payment.Card;

import com.example.hospitalproject.Dto.Payment.Card.CardChangeResponseDto;
import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Dto.Payment.Card.CardInquiryResponseDto;
import com.example.hospitalproject.Entity.Payment.Credit.BankType;
import com.example.hospitalproject.Entity.Payment.Credit.Card;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.Payment.*;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Repository.Payment.Card.CardRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    /**
     * 카드 등록하기
     */
    @Transactional
    public void cardRegistration(CardInfoRequestDto cardInfoRequestDto, User user){
        bankTypeCheck(cardInfoRequestDto.getBank());
        cardInfoMatchCheck(cardInfoRequestDto);
        Card card = new Card(
                user,
                cardInfoRequestDto.getBank(),
                cardInfoRequestDto.getCardNumber(),
                cardInfoRequestDto.getValidYear(),
                cardInfoRequestDto.getValidMonth(),
                cardInfoRequestDto.getPassword(),
                selectCardCheck(cardInfoRequestDto.getSelectCard()));

        cardRepository.save(card);
    }

    /**
     * 등록된 카드 중 선택 할 수 있도록 기능 추가
     */
    @Transactional
    public void cardChoice(long id, User user){
        Card card = cardRepository.findByIdAndUser_Username(id, user.getUsername()).orElseThrow(() -> {
            throw new NotFoundCardException("등록되지 않은 카드입니다.");
        });
        if(card.getSelectCard().equals("선택")){
            throw new CardSameStatusException();
        }
        if(selectCardListCheck(user)) {
            card.setSelectCard("선택");
        }
    }

    /**
     * 등록한 카드 리스트 조회
     */
    @Transactional(readOnly = true)
    public List<CardInquiryResponseDto> getMyCardList(User user){
        List<Card> card = cardRepository.findAllByUser(user);
        List<CardInquiryResponseDto> cardList = new LinkedList<>();
        card.forEach(value -> cardList.add(new CardInquiryResponseDto().toDo(value)));
        return cardList;
    }

    /**
     * 사용할 카드로 선택한 카드 조회
     */
    @Transactional(readOnly = true)
    public CardInquiryResponseDto getMyCard(User user){
        List<Card> card = cardRepository.findAllByUser(user);
        if(card.stream().filter(value -> value.getSelectCard().equals("선택")).count() == 1){
            return new CardInquiryResponseDto().toDo(card.stream().filter(value -> value.getSelectCard().equals("선택")).findAny().orElseThrow());
        }
        throw new NotFoundCardChoiceException();
    }

    /**
     * 사용할 카드 변경
     */
    @Transactional
    public CardChangeResponseDto changeMyChoiceCard(long id, User user){
        // 이전에 선택된 카드
        Card cardBefore = cardRepository.findById(getMyCard(user).getId()).orElseThrow(() -> {
            throw new NotFoundCardException("카드가 존재하지 않습니다.");
        });
        cardBefore.setSelectCard("미선택");
        usernameAuthCompareCheck(user.getUsername(), cardBefore.getUser().getUsername());
        CardInquiryResponseDto beforeChoiceCard = new CardInquiryResponseDto().toDo(cardBefore);
        // 변경 후 선택된 카드
        Card cardAfter = cardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundCardException("등록된 카드가 존재하지 않습니다.");
        });
        cardAfter.setSelectCard("선택");
        usernameAuthCompareCheck(user.getUsername(), cardAfter.getUser().getUsername());
        CardInquiryResponseDto afterChoiceCard = new CardInquiryResponseDto().toDo(cardAfter);
        return new CardChangeResponseDto().toDo(beforeChoiceCard, afterChoiceCard);
    }

    /**
     * 등록한 카드가 하나이고 선택상태를 미선택으로 바꾸고 싶은 경우
     */
    @Transactional
    public CardInquiryResponseDto cardChoiceChange(long id, User user){
        Card card = cardRepository.findByIdAndUser_Username(id, user.getUsername()).orElseThrow(() -> {
            throw new NotFoundUserException("일치하는 정보가 존재하지 않습니다.");
        });
        card.setSelectCard("미선택");
        return new CardInquiryResponseDto().toDo(card);
    }

    /**
     * 등록된 카드 삭제
     */
    @Transactional
    public void deleteRegistrationCard(long id, User user){
        cardRepository.findByIdAndUser_Username(id, user.getUsername()).orElseThrow(() -> {
            throw new NotFoundCardException("해당 카드를 찾을 수 없습니다.");
        });
        cardRepository.deleteById(id);
    }

    /**
     * enum 타입으로 등록될 수 있는 은행인지 확인
     */
    protected void bankTypeCheck(String bank){
        if(BankType.searchBankType(bank).getBankName().equals("없음")){
            throw new NotFoundBankException();
        }
    }

    /**
     * 입력과 똑같은 카드 리스트 추출
     */
    protected void cardInfoMatchCheck(CardInfoRequestDto cardInfoRequestDto){
        List<Card> cardList = cardRepository.findByCardNumber(cardInfoRequestDto.getCardNumber());
        if(!cardList.isEmpty()){
            cardList.forEach(card -> cardNumberCheck(card, cardInfoRequestDto));
        }
    }

    /**
     * 이미 등록된 카드인지 확인
     */
    protected void cardNumberCheck(Card card, CardInfoRequestDto cardInfoRequestDto){
        if(cardInfoRequestDto.getBank().equals(card.getBank())
                && cardInfoRequestDto.getValidYear().equals(card.getValidYear())
                && cardInfoRequestDto.getValidMonth().equals(card.getValidMonth())){
            throw new DuplicateCardInfoException();
        }
    }

    /**
     * 카드 등록시 바로 사용할 카드로 선택할 것인지 선택여부
     */
    protected String selectCardCheck(String cardChoice){
        if(!cardChoice.equals("선택")){
            return "미선택";
        }
        return "선택";
    }

    /**
     * 본인 카드 목록 중에 선택된 카드가 있는지 확인하는 함수 (등록될 카드는 한장이여야 한다.)
     */
    protected boolean selectCardListCheck(User user){
        List<Card> cardList = cardRepository.findAllByUser(user);
        if(cardList.isEmpty()){
            throw new NotFoundCardListException();
        }
        if(cardList.stream().noneMatch(card -> card.getSelectCard().equals("선택"))){
            return true;
        }
        return false;
    }

    /**
     * 카드 변경할때 본인이 등록한 카드인지 확인하고 바꾸기
     */
    protected void usernameAuthCompareCheck(String username, String compareUsername){
        if(username.equals(compareUsername)){
            return;
        }
        throw new NotFoundUserException("카드 등록자와 일치하지 않습니다.");
    }

}
