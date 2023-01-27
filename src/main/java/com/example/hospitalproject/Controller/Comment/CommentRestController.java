package com.example.hospitalproject.Controller.Comment;

import com.example.hospitalproject.Dto.Comment.CommentCreateRequestDto;
import com.example.hospitalproject.Dto.Comment.CommentEditRequestDto;
import com.example.hospitalproject.Service.Comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController             //json 형식
@RequiredArgsConstructor    //필요한 생성자
@RequestMapping("/comment") //공통으로 다 가져가는 Mapping주소 (필수x)
public class CommentRestController {
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void commentCreate(@RequestBody @Valid CommentCreateRequestDto commentCreateRequestDto, @PathVariable long id){
        commentService.commentCreate(commentCreateRequestDto, id);  //(@RequestBody)json데이터가 검증단계를 거치고 service로 넘어감
    }

    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void commentEdit(@RequestBody @Valid CommentEditRequestDto commentEditRequestDto, @PathVariable long id){
        commentService.commentEdit(commentEditRequestDto, id);
    }

    @DeleteMapping("/remove/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void commentRemove(@PathVariable long id){
        commentService.commentRemove(id);
    }

}


// put, del 맵핑
// url 주소는 /remove 이런식으로, @ResponseStatus(HttpStatus.OK), 함수명은 편하게
// 입력이 json 형식이면 @RequestBody @Valid 사용, @PathVariable long id