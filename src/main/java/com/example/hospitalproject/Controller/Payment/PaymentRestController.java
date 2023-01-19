package com.example.hospitalproject.Controller.Payment;

import com.example.hospitalproject.Service.Payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentRestController {
    private final PaymentService paymentService;
}
