package com.example.hospitalproject.Controller.Payment;

import com.example.hospitalproject.Dto.Payment.Card.PayChargeRequestDto;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Service.Payment.PaymentService;
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
@RequestMapping("/payment")
public class PaymentRestController {
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    /**
     * 결제 진행
     */
    @PostMapping("/common/charge")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "결제 진행", notes = "소비자가 결제를 진행")
    public void payCharge(@RequestBody @Valid PayChargeRequestDto payChargeRequestDto){
        User user = userTokenValidCheck();
        paymentService.payCharge(payChargeRequestDto, user);
    }

    /**
     * 결제 취소
     */
    @PostMapping("/common/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "결제 취소", notes = "소비자가 결제를 취소")
    @ApiImplicitParam(name = "id", value = "취소할 거래내역", example = "0")
    public void payCancel(@PathVariable long id){
        User user = userTokenValidCheck();
        paymentService.payCancel(id, user);
    }

    /**
     * 결제 취소 승인
     */
    @PostMapping("/manager/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "결제 취소 승인 (관리자/매니저)", notes = "결제 취소 승인")
    @ApiImplicitParam(name = "id", value = "승인할 취소내역", example = "0")
    public void payToDoApproval(@PathVariable long id){
        User user = userTokenValidCheck();
        paymentService.payToDoApproval(id, user);
    }

    private User userTokenValidCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
            throw new NotFoundUserException("해당 유저가 존재하지 않습니다.");
        });
    }
}
