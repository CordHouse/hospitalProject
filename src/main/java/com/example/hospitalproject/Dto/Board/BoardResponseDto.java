package com.example.hospitalproject.Dto.Board;

import com.example.hospitalproject.Dto.Image.ImageDto;
import com.example.hospitalproject.Entity.Board.Board;
import com.example.hospitalproject.Entity.User.RoleUserGrade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private Double starPoint;
    private String writer;
    private Integer viewCount;
    private RoleUserGrade roleUserGrade;
    private List<ImageDto> imageList;

    public BoardResponseDto toDo(Board board){
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .starPoint(board.getStarPoint())
                .writer(board.getWriter())
                .viewCount(board.getViewCount())
                .roleUserGrade(board.getRoleUserGrade())
                .imageList(board.getImageList()
                        .stream()
                        .map(image -> new ImageDto().toDto(image))
                        .collect(Collectors.toList()))
                .build();
    }
}
