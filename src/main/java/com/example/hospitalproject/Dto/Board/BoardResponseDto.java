package com.example.hospitalproject.Dto.Board;

import com.example.hospitalproject.Entity.Board.Board;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private Integer viewCount;
    private RoleUserGrade roleUserGrade;

    public BoardResponseDto toDo(Board board){
        return new BoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getWriter(),
                board.getViewCount(),
                board.getRoleUserGrade()
        );
    }
}
