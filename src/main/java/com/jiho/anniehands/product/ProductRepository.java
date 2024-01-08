package com.jiho.anniehands.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    // 신상품 5개 조회
    List<Product> findTop5ByIsEnabledOrderByCreatedDateDesc(Boolean isEnabled);

    // 상품 하나 조회
    Product findByNo(Long no);

    @Query("SELECT p FROM Product p WHERE p.category.no = :categoryNo OR p.category.parentCategory.no = :categoryNo AND p.isEnabled = true")
    Page<Product> findByCategoryNo(@Param("categoryNo") Integer categoryNo, Pageable pageable);

}

