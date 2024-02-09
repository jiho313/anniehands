package com.jiho.anniehands.domain.product.dto;

import com.jiho.anniehands.domain.image.Image;
import com.jiho.anniehands.domain.image.ImageDto;
import com.jiho.anniehands.domain.product.Product;
import com.jiho.anniehands.domain.productoption.ProductOptionDto;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
@ToString
public class ProductDetailDto {

    private ProductDto productDto;
    private List<ImageDto> imageDtos;
    private List<ProductOptionDto> colorOptions;  // 색상 옵션
    private List<ProductOptionDto> sizeOptions;   // 사이즈 옵션

    public static ProductDetailDto createDto(Product product, String s3BasePath) {
        List<ImageDto> imageDtos = product.getImages().stream()
                .distinct()
                .map((Image image) -> ImageDto.createDto(image, s3BasePath)).toList();

        // 색상 옵션과 사이즈 옵션 분류
        List<ProductOptionDto> colorOptions = product.getProductOptions().stream()
                .filter(option -> "색상".equals(option.getOption().getName()))
                .map(ProductOptionDto::createDto)
                .toList();

        List<ProductOptionDto> sizeOptions = product.getProductOptions().stream()
                .filter(option -> "사이즈".equals(option.getOption().getName()))
                .map(ProductOptionDto::createDto)
                .toList();

        return ProductDetailDto.builder()
                .productDto(ProductDto.createDto(product, s3BasePath))
                .imageDtos(imageDtos)
                .colorOptions(colorOptions.isEmpty() ? Collections.emptyList() : colorOptions)
                .sizeOptions(sizeOptions.isEmpty() ? Collections.emptyList() : sizeOptions)
                .build();
    }

}
