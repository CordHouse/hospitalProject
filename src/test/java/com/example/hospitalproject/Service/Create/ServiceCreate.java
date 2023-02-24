package com.example.hospitalproject.Service.Create;

import com.example.hospitalproject.Dto.ChatBoard.ChatBoardReceiverRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.EditPrivateTitleRequestDto;
import com.example.hospitalproject.Dto.Payment.Card.CardInfoRequestDto;
import com.example.hospitalproject.Dto.Payment.Card.Format.CustomDecimalFormat;
import com.example.hospitalproject.Dto.Payment.Card.PayChargeRequestDto;
import com.example.hospitalproject.Entity.Chatting.ChatBoard;
import com.example.hospitalproject.Entity.Chatting.ChatTitleType;
import com.example.hospitalproject.Entity.Chatting.Chatting;
import com.example.hospitalproject.Entity.Email.EmailToken;
import com.example.hospitalproject.Entity.Payment.Code;
import com.example.hospitalproject.Entity.Payment.Credit.Card;
import com.example.hospitalproject.Entity.Payment.GroupCode;
import com.example.hospitalproject.Entity.Payment.Payment;


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

    public static PayChargeRequestDto createPayChargeRequest() {
        return PayChargeRequestDto.builder()
                .pay(10000)
                .code("체크카드")
                .build();
    }

    public static Payment createPayment() {
        return new Payment(
                createUser().getUsername(),
                new CustomDecimalFormat(createPayChargeRequest().getPay()).getPaySplit(),
                GroupCode.findGroupCode(Code.findCodeType(createPayChargeRequest().getCode()).getCode()).name(),
                Code.findCodeType(createPayChargeRequest().getCode()).getCode(),
                createPayChargeRequest().getCode(),
                createCardInit(),
                "승인"
        );
    }

    public static ChattingCommentRequestDto createChattingCommentRequest() {
        return ChattingCommentRequestDto.builder()
                .comment("테스트 채팅")
                .build();
    }

    public static ChatBoard createChatBoard() {
        return new ChatBoard(
                "테스트 채팅방",
                ChatTitleType.PRIVATE_CHAT,
                "Tester",
                "Tester1"
        );
    }

    public static Chatting createChatting() {
        return new Chatting(
                "테스트 답변",
                createChatBoard(),
                "Tester",
                "Tester1"
        );
    }

    public static ChatBoardReceiverRequestDto createChatBoardReceiverDto() {
        return new ChatBoardReceiverRequestDto("Tester1");
    }

    public static EditPrivateTitleRequestDto createEditPrivateTitleDto() {
        return new EditPrivateTitleRequestDto("테스트 변경");
    }

    public static EmailToken createEmailToken(String token) {
        EmailToken emailToken = new EmailToken(createUser().getUsername());
        emailToken.setId(token);
        return emailToken;
    }
}
