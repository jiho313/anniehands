package com.jiho.anniehands.product;

import lombok.*;

@Getter
@Builder
@ToString
public class ProductDto {

    private Long no;
    private String name;
    private String content;
    private String thumbnailPath;
    private Integer price;
    private Integer sale;

    public static ProductDto createDto(Product product) {
        return ProductDto.builder()
                .no(product.getNo())
                .name(product.getName())
                .content(product.getContent())
                .thumbnailPath(product.getThumbnailPath())
                .price(product.getPrice())
                .sale(product.getSale())
                .build();
    }
}
