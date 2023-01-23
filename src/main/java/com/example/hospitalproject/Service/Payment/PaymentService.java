package com.example.hospitalproject.Service.Payment;

import com.example.hospitalproject.Dto.Payment.Card.PayChargeRequestDto;
import com.example.hospitalproject.Entity.Payment.Code;
import com.example.hospitalproject.Entity.Payment.GroupCode;
import com.example.hospitalproject.Entity.Payment.Payment;
import com.example.hospitalproject.Exception.UserException.NotFoundUsernameException;
import com.example.hospitalproject.Repository.Payment.Card.CardRepository;
import com.example.hospitalproject.Repository.Payment.PaymentRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    /**
     * 결제 진행
     * @param payChargeRequestDto
     */
    @Transactional
    public void payCharge(PayChargeRequestDto payChargeRequestDto){
        // 지불 방법과 코드가 저장할 수 있는 내용인지 먼저 판단
        Code.findCodeType(payChargeRequestDto.getCode()).checkType(payChargeRequestDto.getCode());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Payment payment = new Payment(
                authentication.getName(),
                payChargeRequestDto.getPaySplit(),
                GroupCode.findGroupCode(Code.findCodeType(payChargeRequestDto.getCode()).getCode()).name(),
                Code.findCodeType(payChargeRequestDto.getCode()).getCode(),
                payChargeRequestDto.getCode(),
                cardRepository.findByUser(userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
                    throw new NotFoundUsernameException("해당 계정이 존재하지 않습니다.");
                })),
                "승인"
        );
        paymentRepository.save(payment);
    }
}
