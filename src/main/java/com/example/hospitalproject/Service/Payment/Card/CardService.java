package com.example.hospitalproject.Service.Payment.Card;

import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Entity.Payment.Credit.BankType;
import com.example.hospitalproject.Entity.Payment.Credit.Card;
import com.example.hospitalproject.Exception.Payment.DuplicateCardInfoException;
import com.example.hospitalproject.Exception.Payment.NotFoundBankException;
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
                cardInfoRequestDto.getPassword());

        cardRepository.save(card);
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
}
