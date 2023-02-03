package com.example.hospitalproject.Controller.Payment;

import com.example.hospitalproject.Dto.Payment.Card.PayChargeRequestDto;
import com.example.hospitalproject.Service.Payment.PaymentService;
import com.google.gson.Gson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @DisplayName("결제 진행")
    @Test
    void payCharge() throws Exception {
        // given
        PayChargeRequestDto payChargeRequestDto = new PayChargeRequestDto(10000, "카드");

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/payment/common/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(payChargeRequestDto))
        );

        // then
        resultActions.andExpect(status().isOk());
        verify(paymentService).payCharge(payChargeRequestDto);
    }

    /**
     * 결제 취소
     */
    @DisplayName("결제 취소")
    @Test
    void payCancel() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/payment/common/"+id)
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    /**
     * 결제 취소 승인
     */
    @DisplayName("결제 취소 승인")
    @Test
    void payToDoApproval() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/payment/manager/"+id)
        );

        // then
        resultActions.andExpect(status().isOk());
    }
}
