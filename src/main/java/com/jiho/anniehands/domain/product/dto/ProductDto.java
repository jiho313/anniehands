package com.jiho.anniehands.domain.product.dto;

import com.jiho.anniehands.domain.product.Product;
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

    public static ProductDto createDto(Product product, String s3BasePath) {
        return ProductDto.builder()
                .no(product.getNo())
                .name(product.getName())
                .content(product.getContent())
                .thumbnailPath(s3BasePath + product.getThumbnailPath())
                .price(product.getPrice())
                .sale(product.getSale())
                .build();
    }
}