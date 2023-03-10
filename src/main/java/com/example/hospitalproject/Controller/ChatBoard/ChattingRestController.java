package com.example.hospitalproject.Controller.ChatBoard;

import com.example.hospitalproject.Dto.ChatBoard.ChattingCommentRequestDto;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Response.Response;
import com.example.hospitalproject.Service.ChatBoard.ChattingService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
public class ChattingRestController {
    private final ChattingService chattingService;
    private final UserRepository userRepository;

    /**
     * 채팅방(id)에서 채팅하기
     */
    @ApiOperation(value = "채팅시작", notes = "상대방과 채팅하기 위해 사용")
    @ApiImplicitParam(name = "id", value = "채팅방 번호", example = "0")
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void chatRunStatus(@PathVariable Long id, @RequestBody @Valid ChattingCommentRequestDto chattingCommentRequestDto){
        User user = userTokenValidCheck();
        chattingService.chatRunStatus(id, chattingCommentRequestDto, user);
    }

    /**
     * 채팅방 안의 채팅 조회
     */
    @ApiOperation(value = "채팅방 채팅조회", notes = "채팅방 내부 채팅 조회시 사용")
    @ApiImplicitParam(name = "id", value = "채팅방 번호", example = "0")
    @GetMapping("/{id}/comment")
    @ResponseStatus(HttpStatus.OK)
    private Response getMyChattingList(@PathVariable long id){
        User user = userTokenValidCheck();
        return Response.success(chattingService.getMyChattingList(id, user));
    }

    /**
     * 채팅 삭제
     */
    @ApiOperation(value = "채팅삭제", notes = "채팅내용 삭제하기 위해 사용")
    @ApiImplicitParam(name = "id", value = "채팅방 번호", example = "0")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void chatDelete(@PathVariable Long id){
        User user = userTokenValidCheck();
        chattingService.chatDelete(id, user);
    }

    /**
     * 유저 토큰
     */
    private User userTokenValidCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
            throw new NotFoundUserException("해당 유저가 존재하지 않습니다.");
        });
    }
}
