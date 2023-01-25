package com.example.hospitalproject.Controller.Comment;

import com.example.hospitalproject.Dto.Comment.CommentCreateRequestDto;
import com.example.hospitalproject.Service.Comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController             //json 형식
@RequiredArgsConstructor    //필요한 생성자
@RequestMapping("/comment")
public class CommentRestController {
    private final CommentService commentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void commentCreate(@RequestBody @Valid CommentCreateRequestDto commentCreateRequestDto){
        commentService.commentCreate(commentCreateRequestDto);  //(@RequestBody)json데이터가 검증단계를 거치고 service로 넘어감
    }
}
