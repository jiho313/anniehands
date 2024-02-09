package com.jiho.anniehands.domain.product.dto;

import com.jiho.anniehands.domain.product.Product;
import com.jiho.anniehands.domain.productoption.ProductOptionDto;
import lombok.*;

import java.util.List;

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
    private List<ProductOptionDto> productOptionDtos;

    // TODO: 보통 조회용 DTO는 'Entity -> DTO'로의  팩토리 메서드
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
