package com.example.hospitalproject.Dto.Board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardViewCountRequestDto {
    private Integer viewCount;

    public void updateVisit(Integer viewCount){
        this.viewCount = viewCount;
    }
}
