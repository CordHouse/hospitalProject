package com.example.hospitalproject.Controller.Payment.Card;

import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Response.Response;
import com.example.hospitalproject.Service.Payment.Card.CardService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/card")
public class CardRestController {
    private final CardService cardService;
    private final UserRepository userRepository;

    /**
     * 신용카드 등록
     */
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "카드 등록", notes = "신용카드 등록")
    public void cardRegistration(@RequestBody @Valid CardInfoRequestDto cardInfoRequestDto){
        User user = userTokenValidCheck();
        cardService.cardRegistration(cardInfoRequestDto, user);
    }

    /**
     * 등록한 카드 중 사용할 카드 선택하기
     */
    @PostMapping("/choice/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "사용할 카드 선택", notes = "등록된 카드 중 사용할 카드 선택")
    @ApiImplicitParam(name = "id", value = "등록된 카드번호", example = "0")
    public void cardChoice(@PathVariable long id){
        User user = userTokenValidCheck();
        cardService.cardChoice(id, user);
    }

    /**
     * 등록한 카드 리스트 조회
     */
    @GetMapping("/my/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "등록된 카드 조회", notes = "등록한 카드 리스트 조회")
    public Response getMyCardList(){
        User user = userTokenValidCheck();
        return Response.success(cardService.getMyCardList(user));
    }

    /**
     * 현재 사용으로 선택한 카드 조회
     */
    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "사용 중인 카드 조회", notes = "현재 사용으로 선택한 카드 조회")
    public Response getMyCard(){
        User user = userTokenValidCheck();
        return Response.success(cardService.getMyCard(user));
    }

    /**
     * 선택한 카드 변경
     * id는 변경할 카드 번호
     */
    @PutMapping("/my/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "선택된 카드 변경", notes = "사용중인 카드 변경")
    @ApiImplicitParam(name = "id", value = "변경할 카드 번호", example = "0")
    public Response changeMyChoiceCard(@PathVariable long id){
        User user = userTokenValidCheck();
        return Response.success(cardService.changeMyChoiceCard(id, user));
    }

    /**
     * 등록된 카드가 하나이고 카드를 미선택으로 변경하고 싶을때
     */
    @PutMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "카드가 하나일때 미선택으로 변경", notes = "등록된 카드가 하나일 때 미선택으로 변경")
    @ApiImplicitParam(name = "id", value = "카드 번호", example = "0")
    public Response cardChoiceChange(@PathVariable long id){
        User user = userTokenValidCheck();
        return Response.success(cardService.cardChoiceChange(id, user));
    }

    /**
     * 등록한 카드 삭제
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "등록한 카드 삭제", notes = "등록한 카드 삭제")
    @ApiImplicitParam(name = "id", value = "카드 번호", example = "0")
    public void deleteRegistrationCard(@PathVariable long id){
        User user = userTokenValidCheck();
        cardService.deleteRegistrationCard(id, user);
    }

    private User userTokenValidCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
            throw new NotFoundUserException("해당 유저가 존재하지 않습니다.");
        });
    }
}
