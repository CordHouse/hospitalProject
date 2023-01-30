package com.example.hospitalproject.Controller.ChatBoard;

import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentRequestDto;
import com.example.hospitalproject.Response.Response;
import com.example.hospitalproject.Service.ChatBoard.ChattingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
public class ChattingRestController {
    private final ChattingService chattingService;

    /**
     * 채팅방(id)에서 채팅하기
     */
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void chatRunStatus(@PathVariable Long id, @RequestBody @Valid ChattingCommentRequestDto chattingCommentRequestDto){
        chattingService.chatRunStatus(id, chattingCommentRequestDto);
    }

    /**
     * 채팅방 안의 채팅 조회
     */
    @GetMapping("/{id}/comment")
    @ResponseStatus(HttpStatus.OK)
    private Response getMyChattingList(@PathVariable long id){
        return Response.success(chattingService.getMyChattingList(id));
    }

    /**
     * 채팅 삭제
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void chatDelete(@PathVariable Long id){
        chattingService.chatDelete(id);
    }
}
