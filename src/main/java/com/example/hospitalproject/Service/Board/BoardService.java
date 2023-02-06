package com.example.hospitalproject.Service.Board;

import com.example.hospitalproject.Dto.Board.BoardChangeRequestDto;
import com.example.hospitalproject.Dto.Board.BoardCreateRequestDto;
import com.example.hospitalproject.Dto.Board.BoardResponseDto;
import com.example.hospitalproject.Entity.Board.Board;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import com.example.hospitalproject.Exception.Board.NotFoundBoardException;
import com.example.hospitalproject.Exception.Board.UserNameDifferentException;
import com.example.hospitalproject.Repository.Board.BoardRepository;
import com.example.hospitalproject.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(BoardCreateRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Board board = new Board();

        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setStarPoint(requestDto.getStarPoint());

        // 별점 수정
        requestDto.todo(Double.parseDouble(requestDto.getStarPoint()));

        board.setWriter(authentication.getName());
        board.setRoleUserGrade(RoleUserGrade.findUserGrade(
                authentication.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.joining())
        ));

        boardRepository.save(board);
    }

    @Transactional
    public void delete(Long id){
        boardRepository.findById(id).orElseThrow(NotFoundBoardException::new);
        boardRepository.deleteById(id);
    }

    @Transactional
    public void change(Long id, BoardChangeRequestDto boardChangeRequestDto){
        Board board = boardRepository.findById(id).orElseThrow(NotFoundBoardException::new);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String user = authentication.getName();
        String boardUser = board.getWriter();

        if(!user.equals(boardUser)){
            throw new UserNameDifferentException();
        }

        board.setTitle(boardChangeRequestDto.getTitle());
        board.setContent(boardChangeRequestDto.getContent());
        board.setStarPoint(boardChangeRequestDto.getStarPoint());

        // 별점 수정
        boardChangeRequestDto.todo(Double.parseDouble(boardChangeRequestDto.getStarPoint()));
    }

    @Transactional
    public BoardResponseDto getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundBoardException();
        });

        board.setViewCount(board.getViewCount() + 1);

        return new BoardResponseDto().toDo(board);
    }
}
