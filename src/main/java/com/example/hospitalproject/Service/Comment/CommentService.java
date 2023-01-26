package com.example.hospitalproject.Service.Comment;

import com.example.hospitalproject.Dto.Comment.CommentCreateRequestDto;
import com.example.hospitalproject.Entity.Comment.Comment;
import com.example.hospitalproject.Exception.Board.NotFoundBoardException;
import com.example.hospitalproject.Repository.Board.BoardRepository;
import com.example.hospitalproject.Repository.Comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional  //데이터베이스에 저장하기 전 안전장치
    public void commentCreate(CommentCreateRequestDto commentCreateRequestDto, long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //로그인한 아이디 쓰고싶을때
        Comment comment = new Comment(commentCreateRequestDto.getComment(), authentication.getName(),
                boardRepository.findById(id).orElseThrow(()->{
                    throw new NotFoundBoardException();
                }));
        //orElseThrow() 만약에 없으면 에러메시지 출력
        commentRepository.save(comment);

    }

}
