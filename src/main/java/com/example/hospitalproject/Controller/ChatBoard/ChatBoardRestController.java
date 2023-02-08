package com.example.hospitalproject.Controller.ChatBoard;

import com.example.hospitalproject.Dto.ChatBoard.ChatBoardReceiverRequestDto;
import com.example.hospitalproject.Dto.ChatBoard.EditPrivateTitleRequestDto;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Response.Response;
import com.example.hospitalproject.Service.ChatBoard.ChatBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatBoardRestController {
    private final ChatBoardService chatBoardService;
    private final UserRepository userRepository;

    /**
     * 서비스 센터 (고객센터) 채팅방
     * */
    @PostMapping("/create/service/center")
    @ResponseStatus(HttpStatus.OK)
    public void createChatBoard(){
        User user = userTokenValidCheck();
        chatBoardService.createServiceCenterChatBoard(user);
    }

    /**
     * 개인 채팅방 (1:1)
     */
    @PostMapping("/create/private")
    @ResponseStatus(HttpStatus.OK)
    public void createPrivateChatBoard(@RequestBody @Valid ChatBoardReceiverRequestDto chatBoardReceiverRequestDto){
        User user = userTokenValidCheck();
        chatBoardService.createPrivateChatBoard(chatBoardReceiverRequestDto, user);
    }

    /**
     * 개인 채팅방 제목 변경
     */
    @PutMapping("/change/title/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editPrivateTitle(@RequestBody @Valid EditPrivateTitleRequestDto editPrivateTitleRequestDto, @PathVariable long id){
        User user = userTokenValidCheck();
        chatBoardService.editPrivateTitle(editPrivateTitleRequestDto, id, user);
    }

    /**
     * 채팅방 목록 가져오기
     */
    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    private Response getMyChatBoardList(){
        User user = userTokenValidCheck();
        return Response.success(chatBoardService.getMyChatBoardList(user));
    }

    /**
     * 채팅방 삭제 -> 진짜로 삭제되는 것은 아니고, 데이터 보관을 위해 true, false로 여부만 확인한다.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChatBoard(@PathVariable long id){
        User user = userTokenValidCheck();
        chatBoardService.deleteChatBoard(id, user);
    }

    private User userTokenValidCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
            throw new NotFoundUserException("해당 유저가 존재하지 않습니다.");
        });
    }
}
