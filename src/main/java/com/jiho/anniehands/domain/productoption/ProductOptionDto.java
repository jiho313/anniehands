package com.jiho.anniehands.domain.productoption;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ProductOptionDto {

    private Long no;
    private String optionName;
    private String optionValue;

    public static ProductOptionDto createDto(ProductOption productOption) {
        return ProductOptionDto.builder()
                .no(productOption.getNo())
                .optionName(productOption.getOption().getName())
                .optionValue(productOption.getOption().getValue())
                .build();
    }
}
