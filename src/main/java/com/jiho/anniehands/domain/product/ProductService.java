package com.jiho.anniehands.domain.product;

import com.jiho.anniehands.common.exception.CustomErrorCode;
import com.jiho.anniehands.common.exception.PageException;
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

    public ProductDetailDto findProductByNo(Long no) {
        Product product = productRepository.findByNo(no)
                .orElseThrow(() -> new PageException(CustomErrorCode.NOT_FOUND_PRODUCT));
        return ProductDetailDto.createDto(product, s3ProductsPath);
    }

    // 신상품 5개 조회
    public List<ProductDto> getTop5NewProducts() {
        return productRepository.findTop5ByIsEnabledOrderByCreatedDateDesc(true).stream()
                .map(product -> ProductDto.createDto(product, s3ProductsPath)).toList();
    }


}
