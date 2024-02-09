package com.jiho.anniehands.domain.options;

import com.jiho.anniehands.domain.productoption.ProductOptionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OptionsDto {

    private final List<ProductOptionDto> colorOptions = new ArrayList<>();  // 색상 옵션
    private final List<ProductOptionDto> sizeOptions = new ArrayList<>();   // 사이즈 옵션

    public static OptionsDto createDto(List<Options> options) {
        OptionsDto optionsDto = new OptionsDto();
        for (Options option : options) {
            if ("색상".equals(option.getName())) {
                optionsDto.getColorOptions().add(
                        ProductOptionDto.builder()
                                .no(Long.valueOf(option.getNo()))
                                .optionName(option.getName())
                                .optionValue(option.getValue())
                                .build()
                );
            } else if ("사이즈".equals(option.getName())) {
                optionsDto.getSizeOptions().add(
                        ProductOptionDto.builder()
                                .no(Long.valueOf(option.getNo()))
                                .optionName(option.getName())
                                .optionValue(option.getValue())
                                .build()
                );
            }
        }
        return optionsDto;
    }

}
