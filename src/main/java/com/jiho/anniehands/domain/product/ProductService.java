package com.jiho.anniehands.domain.product;

import com.jiho.anniehands.global.exception.CustomErrorCode;
import com.jiho.anniehands.global.exception.PageException;
import com.jiho.anniehands.domain.product.dto.ProductDetailDto;
import com.jiho.anniehands.domain.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<ProductDto> getTop5NewProducts(Pageable pageable) {
        return productRepository.findTop5ByIsEnabled(true, pageable)
                .map(product -> ProductDto.createDto(product, s3ProductsPath));
    }


}
