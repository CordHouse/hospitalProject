package com.example.hospitalproject.Service.Board;

import com.example.hospitalproject.Dto.Board.BoardCreateRequestDto;
import com.example.hospitalproject.Entity.Board.Board;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import com.example.hospitalproject.Repository.Board.BoardRepository;
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

    @Transactional
    public void create(BoardCreateRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Board board = new Board();

        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setWriter(authentication.getName());
        board.setRoleUserGrade(RoleUserGrade.findUserGrade(
                authentication.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.joining())
        ));

        boardRepository.save(board);
    }

}
