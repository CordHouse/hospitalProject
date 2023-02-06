package com.example.hospitalproject.Service.Create;

import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Entity.Payment.Credit.Card;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static com.example.hospitalproject.Controller.Create.ControllerCreate.createUser;

public class ServiceCreate {
    public static CardInfoRequestDto createCardInfo() {
        return new CardInfoRequestDto(
                "국민은행",
                "1234-1234-1234-1234",
                "25",
                "25",
                "12",
                "선택");
    }

    public static Card createCardInit() {
        return new Card(
                createUser(),
                createCardInfo().getBank(),
                createCardInfo().getCardNumber(),
                createCardInfo().getValidYear(),
                createCardInfo().getValidMonth(),
                createCardInfo().getPassword(),
                createCardInfo().getSelectCard()
        );
    }

    public static Authentication authentication() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("test", "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
