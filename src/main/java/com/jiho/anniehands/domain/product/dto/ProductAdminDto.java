package com.jiho.anniehands.domain.product.dto;

import com.jiho.anniehands.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ProductAdminDto {

    private Long no;
    private String name;
    private String content;
    private String thumbnailPath;
    private Integer price;
    private Integer sale;
    private Integer stock;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isEnabled;

    public static ProductAdminDto createDto(Product product, String s3BasePath) {
        return ProductAdminDto.builder()
                .no(product.getNo())
                .name(product.getName())
                .content(product.getContent())
                .thumbnailPath(s3BasePath + product.getThumbnailPath())
                .price(product.getPrice())
                .sale(product.getSale())
                .stock(product.getStock())
                .createdDate(product.getCreatedDate())
                .updatedDate(product.getUpdatedDate())
                .isEnabled(product.getIsEnabled())
                .build();
    }
}
