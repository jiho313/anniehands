package com.jiho.anniehands.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 번호로 상품조회
    public Product getProductByNo(Long no) {
        return this.productRepository.findByNo(no);
    }

    // 신상품 5개 조회
    public List<Product> getTop5NewProducts() {
        return this.productRepository.findTop5ByIsEnabledOrderByCreatedDateDesc(true);
    }


}
