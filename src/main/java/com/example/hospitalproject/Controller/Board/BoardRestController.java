package com.example.hospitalproject.Controller.Board;

import com.example.hospitalproject.Dto.Board.BoardCreateRequestDto;
import com.example.hospitalproject.Response.Response;
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

    @PostMapping("/board")
    @ResponseStatus(HttpStatus.OK)
    public Response create(@RequestBody @Valid BoardCreateRequestDto boardCreateRequestDto){
        return Response.success();
    }

    @DeleteMapping("/board/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id){
        return Response.success();
    }


}
