package com.example.hospitalproject.Service.Payment;

import com.example.hospitalproject.Dto.Payment.Card.PayChargeRequestDto;
import com.example.hospitalproject.Entity.Payment.Payment;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Repository.Payment.Card.CardRepository;
import com.example.hospitalproject.Repository.Payment.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.hospitalproject.Controller.Create.ControllerCreate.createUser;
import static com.example.hospitalproject.Service.Create.ServiceCreate.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CardRepository cardRepository;

    /**
     * 결제 진행
     */
    @DisplayName("결제 진행")
    @Test
    void payChargeTest() {
        // given
        PayChargeRequestDto payChargeRequestDto = createPayChargeRequest();
        User user = createUser();
        Payment payment = createPayment();
        given(cardRepository.findByUser(user)).willReturn(createCardInit());

        // when
        paymentService.payCharge(payChargeRequestDto, user);

        // then
        verify(cardRepository).findByUser(user);
        verify(paymentRepository).save(payment);
    }

    /**
     * 거래 취소 신청
     */
    @DisplayName("거래 취소 신청")
    @Test
    void payCancelTest() {
        // given
        Long id = 1L;
        Payment payment = createPayment();
        User user = createUser();

        given(paymentRepository.findByIdAndUsername(id, user.getUsername())).willReturn(Optional.of(payment));

        // when
        paymentService.payCancel(id, user);

        // then
        assertThat(payment.getApproval()).isEqualTo("취소 신청");
        assertThat(payment.getApprovalStatus()).isEqualTo("미승인");
        verify(paymentRepository).findByIdAndUsername(id, user.getUsername());
    }

    /**
     * 거래 취소 처리 ( 관리자, 매니저만 가능 )
     */
    @DisplayName("거래 취소 처리 ( 관리자, 매니저만 가능 )")
    @Test
    void payToDoApprovalTest() {
        // given
        Long id = 1L;
        Payment payment = createPayment();
        payment.setApproval("취소 신청");
        User user = createUser();
        user.setRoleUserGrade(RoleUserGrade.ROLE_ADMIN);

        given(paymentRepository.findByIdAndUsername(id, user.getUsername())).willReturn(Optional.of(payment));

        // when
        paymentService.payToDoApproval(id, user);

        // then
        assertThat(payment.getApproval()).isEqualTo("취소 완료");
        assertThat(payment.getAdminName()).isEqualTo("Tester");
        assertThat(payment.getApprovalStatus()).isEqualTo("취소 승인 완료");
    }
}
