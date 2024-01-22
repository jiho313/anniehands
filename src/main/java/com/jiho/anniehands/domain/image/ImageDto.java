package com.jiho.anniehands.domain.image;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ImageDto {

    private Long no;
    private String originName;
    private String serverName;

    public static ImageDto createDto(Image image, String s3BasePath ) {
        return ImageDto.builder()
                .no(image.getNo())
                .originName(image.getOriginName())
                .serverName(s3BasePath + image.getServerName())
                .build();
    }
}
