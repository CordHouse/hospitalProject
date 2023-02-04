package com.example.hospitalproject.Service.Payment;

import com.example.hospitalproject.Repository.Payment.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private PaymentRepository paymentRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void mockMvcInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentService).build();
    }

    /**
     * 결제 진행
     */
    @DisplayName("결제 진행")
    @Test
    void payCharge() {

    }

    /**
     * 거래 취소 신청
     */
    @DisplayName("거래 취소 신청")
    @Test
    void payCancel() {

    }

    /**
     * 거래 취소 처리 ( 관리자, 매니저만 가능 )
     */
    @DisplayName("거래 취소 처리 ( 관리자, 매니저만 가능 )")
    @Test
    void payToDoApproval() {

    }
}
