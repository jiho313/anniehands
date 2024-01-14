package com.jiho.anniehands.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    @Value("${s3.path.products}")
    private String s3ProductsPath;

    // 상품 번호로 상품조회
    public ProductDto getProductByNo(Long no) {
        ProductDto productDto = ProductDto.createDto(productRepository.findByNo(no), s3ProductsPath);
        return productDto;
    }

    // 신상품 5개 조회
    public List<ProductDto> getTop5NewProducts() {
        return productRepository.findTop5ByIsEnabledOrderByCreatedDateDesc(true).stream()
                .map(product -> ProductDto.createDto(product, s3ProductsPath)).toList();
    }


}
