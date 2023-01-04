package com.example.hospitalproject.Controller.ChatBoard;

import com.example.hospitalproject.Service.ChatBoard.ChatBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatBoardRestController {
    private final ChatBoardService chatBoardService;

    @PostMapping("/create/service/center")
    @ResponseStatus(HttpStatus.OK)
    public void createChatBoard(){
        chatBoardService.createServiceCenterChatBoard();
    }

    @PostMapping("/create/private")
    @ResponseStatus(HttpStatus.OK)
    public void createPrivateChatBoard(){
        chatBoardService.createPrivateChatBoard();
    }
}
