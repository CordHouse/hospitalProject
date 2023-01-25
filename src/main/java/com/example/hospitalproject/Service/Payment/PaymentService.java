package com.example.hospitalproject.Service.Payment;

import com.example.hospitalproject.Exception.Payment.PayCancelException;
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

import java.time.LocalDateTime;

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

    /**
     * 거래 취소 신청
     * @param id
     */
    @Transactional
    public void payCancel(long id){
        Payment payment = DoPaymentFindById(id);
        if(!payment.getApproval().equals("승인")){
            throw new PayCancelException("취소 할 수 없는 거래내역 입니다.");
        }
        payment.setApproval("취소 신청");
        payment.setApprovalDate(LocalDateTime.now());
        payment.setApprovalStatus("미승인");
    }

    /**
     * 거래 취소 처리 ( 관리자, 매니저만 가능 )
     * @param id
     */
    @Transactional
    public void payToDoApproval(long id){
        Payment payment = DoPaymentFindById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(payment.getApproval().equals("취소 신청")){
            payment.setApproval("취소 완료");
            payment.setAdminName(authentication.getName());
            payment.setApprovalStatus("취소 승인 완료");
            payment.setApprovalStatusDate(LocalDateTime.now());
        }
    }

    protected Payment DoPaymentFindById(long id){
        return paymentRepository.findById(id).orElseThrow(() -> {
            throw new PayCancelException("거래내역이 존재하지 않습니다.");
        });
    }
}
