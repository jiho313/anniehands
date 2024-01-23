package com.jiho.anniehands.domain.product;

import com.jiho.anniehands.domain.image.Image;
import com.jiho.anniehands.domain.image.ImageDto;
import com.jiho.anniehands.domain.productoptions.ProductOptionsDto;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
@ToString
public class ProductDetailDto {

    private ProductDto productDto;
    private List<ImageDto> imageDtos;
    private List<ProductOptionsDto> colorOptions;  // 색상 옵션
    private List<ProductOptionsDto> sizeOptions;   // 사이즈 옵션

    public static ProductDetailDto createDto(Product product, String s3BasePath) {
        List<ImageDto> imageDtos = product.getImages().stream()
                .map((Image image) -> ImageDto.createDto(image, s3BasePath)).toList();

        // 색상 옵션과 사이즈 옵션 분류
        List<ProductOptionsDto> colorOptions = product.getProductOptions().stream()
                .filter(option -> "색상".equals(option.getOption().getName()))
                .map(ProductOptionsDto::createDto)
                .toList();

        List<ProductOptionsDto> sizeOptions = product.getProductOptions().stream()
                .filter(option -> "사이즈".equals(option.getOption().getName()))
                .map(ProductOptionsDto::createDto)
                .toList();

        return ProductDetailDto.builder()
                .productDto(ProductDto.createDto(product, s3BasePath))
                .imageDtos(imageDtos)
                .colorOptions(colorOptions.isEmpty() ? Collections.emptyList() : colorOptions)
                .sizeOptions(sizeOptions.isEmpty() ? Collections.emptyList() : sizeOptions)
                .build();
    }

}
