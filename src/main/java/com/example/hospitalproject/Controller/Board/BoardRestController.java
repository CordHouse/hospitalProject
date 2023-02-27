package com.example.hospitalproject.Controller.Board;

import com.example.hospitalproject.Dto.Board.BoardChangeRequestDto;
import com.example.hospitalproject.Dto.Board.BoardCreateRequestDto;
import com.example.hospitalproject.Dto.Board.BoardStarPointRequestDto;
import com.example.hospitalproject.Entity.User.User;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Repository.User.UserRepository;
import com.example.hospitalproject.Response.Response;
import com.example.hospitalproject.Service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardRestController {
    private final BoardService boardService;
    private final UserRepository userRepository;

    /**
     * 게시물 생성
     * 댓글 생성 검증
     * @param boardCreateRequestDto
     */
    @PostMapping("/board")
    @ResponseStatus(HttpStatus.OK)
    public void create(@ModelAttribute @Valid BoardCreateRequestDto boardCreateRequestDto){
        boardService.create(boardCreateRequestDto);
    }

    /**
     * 게시물 삭제
     * 댓글 삭제 검증
     * @param id
     */
    @DeleteMapping("/board/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        boardService.delete(id);
    }

    /**
     * 게시물 수정
     * 댓글 수정 검증
     * @param id
     * @param boardChangeRequestDto
     */
    @PutMapping("/board/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void change(@PathVariable Long id, @ModelAttribute @Valid BoardChangeRequestDto boardChangeRequestDto){
        boardService.change(id, boardChangeRequestDto);
    }

    /**
     * 게시물 조회
     * @param id
     */
    @GetMapping("/board/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getBoard(@PathVariable Long id){
        return Response.success(boardService.getBoard(id));
    }

    /**
     * 별점 입력
     * @param id
     * @param boardStarPointRequestDto
     */
    @PostMapping("/board/{id}/starpoint")
    @ResponseStatus(HttpStatus.OK)
    public void inputStarPoint(@PathVariable Long id, @RequestBody @Valid BoardStarPointRequestDto boardStarPointRequestDto){
        boardService.inputStarPoint(id, boardStarPointRequestDto);
    }

    private User userTokenValidCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
            throw new NotFoundUserException("해당 유저가 존재하지 않습니다.");
        });
    }
}
