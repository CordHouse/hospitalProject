package com.example.hospitalproject.Controller.Payment;

import com.example.hospitalproject.Dto.Payment.Card.PayChargeRequestDto;
import com.example.hospitalproject.Service.Payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentRestController {
    private final PaymentService paymentService;

    /**
     * 결제 진행
     */
    @PostMapping("/common/charge")
    @ResponseStatus(HttpStatus.OK)
    public void payCharge(@RequestBody @Valid PayChargeRequestDto payChargeRequestDto){
        paymentService.payCharge(payChargeRequestDto);
    }

    /**
     * 결제 취소
     */
    @PostMapping("/common/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void payCancel(@PathVariable long id){
        paymentService.payCancel(id);
    }

    /**
     * 결제 취소 승인
     */
    @PostMapping("/manager/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void payToDoApproval(@PathVariable long id){
        paymentService.payToDoApproval(id);
    }
}
