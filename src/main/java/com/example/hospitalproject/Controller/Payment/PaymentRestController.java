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
     * @param payChargeRequestDto
     */
    @PostMapping("/charge")
    @ResponseStatus(HttpStatus.OK)
    public void payCharge(@RequestBody @Valid PayChargeRequestDto payChargeRequestDto){
        paymentService.payCharge(payChargeRequestDto);
    }
}
