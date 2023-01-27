package com.example.hospitalproject.Dto.Payment.Card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardChangeResponseDto {
    private CardInquiryResponseDto beforeChoiceCard;
    private CardInquiryResponseDto afterChoiceCard;

    public CardChangeResponseDto toDo(CardInquiryResponseDto beforeChoiceCard, CardInquiryResponseDto afterChoiceCard){
        return new CardChangeResponseDto(beforeChoiceCard, afterChoiceCard);
    }
}
