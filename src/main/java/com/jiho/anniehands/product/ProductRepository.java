package com.jiho.anniehands.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    // 신상품 5개 조회
    List<Product> findTop5ByIsEnabledOrderByCreatedDateDesc(Boolean isEnabled);

    // 상품 하나 조회
    Product findByNo(Long no);
}
