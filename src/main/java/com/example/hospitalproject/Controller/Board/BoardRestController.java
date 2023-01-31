package com.example.hospitalproject.Controller.Board;

import com.example.hospitalproject.Dto.Board.BoardChangeRequestDto;
import com.example.hospitalproject.Dto.Board.BoardCreateRequestDto;
import com.example.hospitalproject.Service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardRestController {
    private final BoardService boardService;

    /**
     * 댓글 생성 검증
     * @param boardCreateRequestDto
     */
    @PostMapping("/board")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody @Valid BoardCreateRequestDto boardCreateRequestDto){
        boardService.create(boardCreateRequestDto);
    }

    /**
     * 댓글 삭제 검증
     * @param id
     */
    @DeleteMapping("/board/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        boardService.delete(id);
    }

    /**
     * 댓글 수정 검증
     * @param id
     * @param boardChangeRequestDto
     */
    @PutMapping("/board/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void change(@PathVariable Long id, @RequestBody @Valid BoardChangeRequestDto boardChangeRequestDto){
        boardService.change(id, boardChangeRequestDto);
    }


}
