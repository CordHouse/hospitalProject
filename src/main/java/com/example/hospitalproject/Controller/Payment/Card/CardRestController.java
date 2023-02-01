package com.example.hospitalproject.Controller.Payment.Card;

import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Response.Response;
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
     */
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public void cardRegistration(@RequestBody @Valid CardInfoRequestDto cardInfoRequestDto){
        cardService.cardRegistration(cardInfoRequestDto);
    }

    /**
     * 등록한 카드 중 사용할 카드 선택하기
     */
    @PostMapping("/choice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void cardChoice(@PathVariable long id){
        cardService.cardChoice(id);
    }

    /**
     * 등록한 카드 리스트 조회
     */
    @GetMapping("/my/list")
    @ResponseStatus(HttpStatus.OK)
    public Response getMyCardList(){
        return Response.success(cardService.getMyCardList());
    }

    /**
     * 현재 사용으로 선택한 카드 조회
     */
    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public Response getMyCard(){
        return Response.success(cardService.getMyCard());
    }

    /**
     * 선택한 카드 변경
     * id는 변경할 카드 번호
     */
    @PutMapping("/my/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response changeMyChoiceCard(@PathVariable long id){
        return Response.success(cardService.changeMyChoiceCard(id));
    }

    /**
     * 등록된 카드가 하나이고 카드를 미선택으로 변경하고 싶을때
     */
    @PutMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response cardChoiceChange(@PathVariable long id){
        return Response.success(cardService.cardChoiceChange(id));
    }

    /**
     * 등록한 카드 삭제
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRegistrationCard(@PathVariable long id){
        cardService.deleteRegistrationCard(id);
    }
}
