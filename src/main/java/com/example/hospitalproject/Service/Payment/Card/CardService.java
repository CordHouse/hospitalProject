package com.example.hospitalproject.Service.Payment.Card;

import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Entity.Payment.Credit.BankType;
import com.example.hospitalproject.Entity.Payment.Credit.Card;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.Payment.DuplicateCardInfoException;
import com.example.hospitalproject.Exception.Payment.NotFoundBankException;
import com.example.hospitalproject.Exception.Payment.NotFoundCardException;
import com.example.hospitalproject.Exception.Payment.NotFoundCardListException;
import com.example.hospitalproject.Exception.UserException.NotFoundUsernameException;
import com.example.hospitalproject.Repository.Payment.Card.CardRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    /**
     * 카드 등록하기
     * @param cardInfoRequestDto
     */
    @Transactional
    public void cardRegistration(CardInfoRequestDto cardInfoRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        bankTypeCheck(cardInfoRequestDto.getBank());
        cardInfoMatchCheck(cardInfoRequestDto);
        Card card = new Card(
                userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
                    throw new NotFoundUsernameException("해당 아이디가 존재하지 않습니다.");
                }),
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
     * @param id
     */
    @Transactional
    public void cardChoice(long id){
        Card card = cardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundCardException("등록되지 않은 카드입니다.");
        });
        if(selectCardListCheck(id)) {
            card.setSelectCard("선택");
        }
    }

    /**
     * enum 타입으로 등록될 수 있는 은행인지 확인
     * @param bank
     */
    protected void bankTypeCheck(String bank){
        if(BankType.searchBankType(bank).getBankName().equals("없음")){
            throw new NotFoundBankException();
        }
    }

    /**
     * 입력과 똑같은 카드 리스트 추출
     * @param cardInfoRequestDto
     */
    protected void cardInfoMatchCheck(CardInfoRequestDto cardInfoRequestDto){
        List<Card> cardList = cardRepository.findByCardNumber(cardInfoRequestDto.getCardNumber()).orElseThrow();
        cardList.forEach(card -> cardNumberCheck(card, cardInfoRequestDto));
    }

    /**
     * 이미 등록된 카드인지 확인
     * @param card
     * @param cardInfoRequestDto
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
     * @param cardChoice
     * @return
     */
    protected String selectCardCheck(String cardChoice){
        if(!cardChoice.equals("선택")){
            return "미선택";
        }
        return "선택";
    }

    /**
     * 본인 카드 목록 중에 선택된 카드가 있는지 확인하는 함수 (등록될 카드는 한장이여야 한다.)
     * @param id
     * @return
     */
    protected boolean selectCardListCheck(long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
            throw new NotFoundUsernameException("존재하지 않는 계정입니다.");
        });
        List<Card> cardList = cardRepository.findAllByUser(user);
        if(cardList.isEmpty()){
            throw new NotFoundCardListException();
        }
        if(cardList.stream().noneMatch(card -> card.getSelectCard().equals("선택"))){
            return true;
        }
        return false;
    }
}
