package com.example.hospitalproject.Service.Board;

import com.example.hospitalproject.Config.image.ImageManager;
import com.example.hospitalproject.Dto.Board.BoardChangeRequestDto;
import com.example.hospitalproject.Dto.Board.BoardCreateRequestDto;
import com.example.hospitalproject.Dto.Board.BoardResponseDto;
import com.example.hospitalproject.Dto.Board.BoardStarPointRequestDto;
import com.example.hospitalproject.Entity.Board.Board;
import com.example.hospitalproject.Entity.Image.Image;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import com.example.hospitalproject.Exception.Board.*;
import com.example.hospitalproject.Repository.Board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ImageManager imageManager;

    @Transactional
    public void create(BoardCreateRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .writer(authentication.getName())
                .roleUserGrade(RoleUserGrade
                        .findUserGrade(authentication.getAuthorities()
                                .stream().map(s -> s.toString())
                                .collect(Collectors.joining())))
                .build();

        if(!requestDto.getFileList().isEmpty()) {
            try {
                List<Image> fileList = imageManager.saveImages(requestDto.getFileList());
                board.setImageList(fileList);
            } catch(IOException e) {
                throw new SaveImageException();
            }
        }

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

        if(!user.equals(boardUser)) {
            throw new UserNameDifferentException();
        }

        board.setTitle(boardChangeRequestDto.getTitle());
        board.setContent(boardChangeRequestDto.getContent());

        try {
            board.setImageList(imageManager.saveImages(boardChangeRequestDto.getImageList()));
        } catch(IOException e) {
            throw new SaveImageException();
        }

    }

    @Transactional
    public BoardResponseDto getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundBoardException();
        });

        // 조회수 증가
        board.setViewCount(board.getViewCount() + 1);

        return new BoardResponseDto().toDo(board);
    }

    @Transactional
    public void inputStarPoint(Long id, BoardStarPointRequestDto boardStarPointRequestDto){
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundBoardException();
        });

        // 별점 범위 1.0 ~ 5.0
        if(boardStarPointRequestDto.getStarPoint() < 1.0 || boardStarPointRequestDto.getStarPoint() > 5.0) {
            throw new NotInputStarPointException();
        }

        // 별점 단위를 0.5 단위로 설정
        if(boardStarPointRequestDto.getStarPoint() % 0.5 != 0){
            throw new StarPointRoundUpException();
        }

        // 별점 유저 수, 총점
        board.setStarPointUserCount(board.getStarPointUserCount() + 1);
        board.setStarPointTotal(board.getStarPointTotal() + boardStarPointRequestDto.getStarPoint());

        starPointCheck(board, boardStarPointRequestDto);
    }

    @Transactional
    protected void starPointCheck(Board board, BoardStarPointRequestDto boardStarPointRequestDto){
        if(board.getStarPoint() == 0){
            board.setStarPoint(boardStarPointRequestDto.getStarPoint());
            return;
        }
        board.setStarPoint(board.getStarPointTotal() / board.getStarPointUserCount());
    }
}
