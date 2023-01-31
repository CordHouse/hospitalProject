package com.example.hospitalproject.Service.Comment;

import com.example.hospitalproject.Dto.Comment.CommentCreateRequestDto;
import com.example.hospitalproject.Dto.Comment.CommentEditRequestDto;
import com.example.hospitalproject.Entity.Comment.Comment;
import com.example.hospitalproject.Entity.Comment.CommentUserLike.CommentUserLike;
import com.example.hospitalproject.Exception.Board.NotFoundBoardException;
import com.example.hospitalproject.Exception.Comment.NotFoundCommentIdException;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Repository.Board.BoardRepository;
import com.example.hospitalproject.Repository.Comment.CommentRepository;
import com.example.hospitalproject.Repository.Comment.CommentUserLikeRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
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
    private final UserRepository userRepository;

    private final CommentUserLikeRepository commentUserLikeRepository;

    /**
     * 댓글 생성
     * @param commentCreateRequestDto
     * @param id
     */
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

    /**
     * 댓글 수정
     * @param commentEditRequestDto
     * @param id
     */
    @Transactional //Service에서 항상 달고 시작
    public void commentEdit(CommentEditRequestDto commentEditRequestDto, long id){
        Comment comment = commentRepository.findById(id).orElseThrow(()->{  //예외처리까지
            throw new NotFoundCommentIdException();
        });
        comment.setComment(commentEditRequestDto.getComment());
    }

    /**
     * 댓글 삭제
     * @param id
     */
    @Transactional
    public void commentRemove(long id){
        commentRepository.deleteById(id);
    }

    /**
     * 댓글 좋아요
     * @param id
     */
    @Transactional
    public void commentLike(long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //로그인한 아이디 쓰고싶을때
        userRepository.findByUsername(authentication.getName()).orElseThrow(()->{
            throw new NotFoundUserException("해당 유저가 없습니다.");
        });
        Comment comment = commentRepository.findById(id).orElseThrow(()->{
           throw new NotFoundCommentIdException();
        });
        CommentUserLike commentUserLike = commentUserLikeRepository.findByUsernameAndComment(authentication.getName(), comment);
        if (commentUserLike == null){
            commentUserLikeRepository.save(new CommentUserLike(comment, authentication.getName(), 1));
            return;
        }
        if(commentUserLike.getLike()==1) {
            commentUserLike.setLike(0);
            comment.setLike(comment.getLike()-1);
            return;
        }
        commentUserLike.setLike(1);
        comment.setLike(comment.getLike()+1);

        // 1. like entity 생성
        // 2. like entity에서 사용자 정보 검색 (id값이랑 user id값을 파라메터로 넘기고, 해당하는 사람이 존재하면 1->0, 없으면 0->1)
        //      애초에 존재하지 않았던 사람은 entity 새로 생성하고 1로 초기값 생성
        // 3. comment_like 도 증감
        // >> save 말고 set 사용(setLike)
    }
}

// commentRepository
// 1. 고유키 > id 이용해서 데이터베이스의 해당하는 줄 찾고 set해서 수정