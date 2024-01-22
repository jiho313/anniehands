package com.jiho.anniehands.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    // 신상품 5개 조회
    List<Product> findTop5ByIsEnabledOrderByCreatedDateDesc(Boolean isEnabled);

    @Query("SELECT DISTINCT p " +
            "FROM Product p " +
            "LEFT JOIN FETCH p.images " +
            "LEFT JOIN FETCH p.productOptions po " +
            "LEFT JOIN FETCH po.option " +
            "WHERE p.no = :no")
    Optional<Product> findByNo(@Param("no") Long no);

    @Query("SELECT p FROM Product p WHERE p.category.no = :categoryNo OR p.category.parentCategory.no = :categoryNo AND p.isEnabled = true")
    Page<Product> findByCategoryNo(@Param("categoryNo") Integer categoryNo, Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images WHERE p.no = :no")
    Product findProductWithImages(@Param("no") Long no);

}

