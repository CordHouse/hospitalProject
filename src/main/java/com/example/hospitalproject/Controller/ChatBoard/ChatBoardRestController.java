package com.example.hospitalproject.Controller.ChatBoard;

import com.example.hospitalproject.Dto.ChatBoard.EditPrivateTitleRequestDto;
import com.example.hospitalproject.Service.ChatBoard.ChatBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatBoardRestController {
    private final ChatBoardService chatBoardService;

    /**
     * 서비스 센터 (고객센터) 채팅방
     * */
    @PostMapping("/create/service/center")
    @ResponseStatus(HttpStatus.OK)
    public void createChatBoard(){
        chatBoardService.createServiceCenterChatBoard();
    }

    /**
     * 개인 채팅방 (1:1)
     */
    @PostMapping("/create/private")
    @ResponseStatus(HttpStatus.OK)
    public void createPrivateChatBoard(){
        chatBoardService.createPrivateChatBoard();
    }

    /**
     * 개인 채팅방 제목 변경
     * @param editPrivateTitleRequestDto
     * @param id (PK)
     */
    @PutMapping("/change/title/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editPrivateTitle(@RequestBody @Valid EditPrivateTitleRequestDto editPrivateTitleRequestDto, @PathVariable long id){
        chatBoardService.editPrivateTitle(editPrivateTitleRequestDto, id);
    }

    /**
     * 채팅방 삭제 -> 진짜로 삭제되는 것은 아니고, 데이터 보관을 위해 true, false로 여부만 확인한다.
     * @param id
     */
    @PostMapping("/delete/chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChatBoard(@PathVariable long id){
        chatBoardService.deleteChatBoard(id);
    }
}
