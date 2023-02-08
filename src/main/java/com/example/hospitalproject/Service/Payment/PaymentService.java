package com.example.hospitalproject.Service.Payment;

import com.example.hospitalproject.Dto.Payment.Card.Format.CustomDecimalFormat;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.AuthorityAccessLimitException;
import com.example.hospitalproject.Exception.Payment.PayCancelException;
import com.example.hospitalproject.Dto.Payment.Card.PayChargeRequestDto;
import com.example.hospitalproject.Entity.Payment.Code;
import com.example.hospitalproject.Entity.Payment.GroupCode;
import com.example.hospitalproject.Entity.Payment.Payment;
import com.example.hospitalproject.Repository.Payment.Card.CardRepository;
import com.example.hospitalproject.Repository.Payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;

    /**
     * 결제 진행
     */
    @Transactional
    public void payCharge(PayChargeRequestDto payChargeRequestDto, User user){
        // 지불 방법과 코드가 저장할 수 있는 내용인지 먼저 판단
        Code.findCodeType(payChargeRequestDto.getCode()).checkType(payChargeRequestDto.getCode());
        Payment payment = new Payment(
                user.getUsername(),
                new CustomDecimalFormat(payChargeRequestDto.getPay()).getPaySplit(),
                GroupCode.findGroupCode(Code.findCodeType(payChargeRequestDto.getCode()).getCode()).name(),
                Code.findCodeType(payChargeRequestDto.getCode()).getCode(),
                payChargeRequestDto.getCode(),
                cardRepository.findByUser(user),
                "승인"
        );
        paymentRepository.save(payment);
    }

    /**
     * 거래 취소 신청
     */
    @Transactional
    public void payCancel(long id, User user){
        Payment payment = DoPaymentFindById(id, user);
        if(!payment.getApproval().equals("승인")){
            throw new PayCancelException("취소 할 수 없는 거래내역 입니다.");
        }
        payment.setApproval("취소 신청");
        payment.setApprovalDate(LocalDateTime.now());
        payment.setApprovalStatus("미승인");
    }

    /**
     * 거래 취소 처리 ( 관리자, 매니저만 가능 )
     */
    @Transactional
    public void payToDoApproval(long id, User user){
        Payment payment = DoPaymentFindById(id, user);
        authorityCheck(user.getRoleUserGrade(), List.of(RoleUserGrade.ROLE_ADMIN, RoleUserGrade.ROLE_MANAGER));
        if(payment.getApproval().equals("취소 신청")){
            payment.setApproval("취소 완료");
            payment.setAdminName(user.getUsername());
            payment.setApprovalStatus("취소 승인 완료");
            payment.setApprovalStatusDate(LocalDateTime.now());
        }
    }

    /**
     * 권한 체크
     */
    protected void authorityCheck(RoleUserGrade userGrade, List<RoleUserGrade> roleUserGrade) {
        if(!roleUserGrade.contains(userGrade)){
            throw new AuthorityAccessLimitException();
        }
    }

    /**
     * 거래 내역이 있는지 찾는다.
     */
    protected Payment DoPaymentFindById(long id, User user){
        return paymentRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(() -> {
            throw new PayCancelException("거래내역이 존재하지 않습니다.");
        });
    }
}
