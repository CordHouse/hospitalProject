package com.example.hospitalproject.Controller.Payment.Card;

import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Service.Payment.Card.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/card")
public class CardRestController {
    private final CardService cardService;

    /**
     * 신용카드 등록
     * @param cardInfoRequestDto
     */
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public void cardRegistration(@RequestBody @Valid CardInfoRequestDto cardInfoRequestDto){
        cardService.cardRegistration(cardInfoRequestDto);
    }
}
