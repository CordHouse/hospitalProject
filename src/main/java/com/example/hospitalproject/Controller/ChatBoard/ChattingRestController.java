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

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void chatRunStatus(@PathVariable Long id, @RequestBody @Valid ChattingCommentRequestDto chattingCommentRequestDto){
        chattingService.chatRunStatus(id, chattingCommentRequestDto);
    }

    @GetMapping("/my/info")
    @ResponseStatus(HttpStatus.OK)
    private Response getMyChattingList(){
        return Response.success(chattingService.getMyChattingList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void chatDelete(@PathVariable Long id){
        chattingService.chatDelete(id);
    }
}
