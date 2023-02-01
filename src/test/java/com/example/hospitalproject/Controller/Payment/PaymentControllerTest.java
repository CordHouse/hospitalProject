package com.example.hospitalproject.Controller.Payment;

import com.example.hospitalproject.Service.Payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {
    @InjectMocks // Mock(가짜객체) 개체 주입
    private PaymentRestController paymentRestController;
    @Mock // Mock 생성
    private PaymentService paymentService;

    // Controller 테스트시 HTTP 호출이 필요한데, 일반적으론 불가능 하기 때문에 아래와 같은 변수와 함수로 생성해야 한다.
    private MockMvc mockMvc;

    @BeforeEach
    public void mockMvcInit(){
        mockMvc = MockMvcBuilders.standaloneSetup(paymentRestController).build();
    }

    /**
     * 결제 진행
     */

    /**
     * 결제 취소
     */

    /**
     * 결제 취소 승인
     */
}
