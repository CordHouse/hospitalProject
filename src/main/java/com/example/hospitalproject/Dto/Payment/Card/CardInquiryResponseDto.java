package com.example.hospitalproject.Dto.Payment.Card;

import com.example.hospitalproject.Entity.Payment.Credit.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInquiryResponseDto {
    private long id;
    private String bank;
    private String cardNumber;
    private String selectCard;

    public CardInquiryResponseDto toDo(Card card){
        return new CardInquiryResponseDto(card.getId(), card.getBank(), cardNumberFilter(card.getCardNumber()), card.getSelectCard());
    }

    private String cardNumberFilter(String cardNumber){
        String[] cardSplit = cardNumber.split("-");
        cardSplit[1] = "*".repeat(4);
        cardSplit[2] = "*".repeat(4);
        return String.join("-", cardSplit);
    }
}
