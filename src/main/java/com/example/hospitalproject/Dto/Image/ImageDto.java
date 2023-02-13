package com.example.hospitalproject.Dto.Image;

import com.example.hospitalproject.Entity.Image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageDto {
    private String originalName;
    private String storedName;

    public ImageDto toDto(Image image) {
        return ImageDto.builder()
                .originalName(image.getOriginalName())
                .storedName(image.getStoredName())
                .build();
    }
}
