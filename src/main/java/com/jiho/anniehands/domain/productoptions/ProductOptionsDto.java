package com.jiho.anniehands.domain.productoptions;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ProductOptionsDto {

    private Long no;
    private String optionName;
    private String optionValue;

    public static ProductOptionsDto createDto(ProductOptions productOption) {
        return ProductOptionsDto.builder()
                .no(productOption.getNo())
                .optionName(productOption.getOption().getName())
                .optionValue(productOption.getOption().getValue())
                .build();
    }
}
