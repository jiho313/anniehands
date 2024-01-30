package com.jiho.anniehands.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    // 신상품 5개 조회
    Page<Product> findTop5ByIsEnabled(Boolean isEnabled, Pageable pageable);

    /*LEFT JOIN은 데이터베이스에서 일종의 "선택적인" 관계를 표현할 때 유용하다.
        특히, 관련된 데이터가 있을 수도 있고 없을 수도 있는 경우에 사용되며, 이를 통해 더 포괄적인 데이터 세트를 얻을 수 있다.
        LEFT 왼쪽을 기준 (FROM절), RIGHT 오른쪽을 기준 (JOIN절)
        ex1) 상품에는 이미지가 있을 수도 있고 없을 수도 있다.
        ex2) 상품에는 옵션이 있을 수도 있고 없을 수도 있다.
     */
    @Query("SELECT DISTINCT p " +
            "FROM Product p " +
            "LEFT JOIN FETCH p.images " +
            "LEFT JOIN FETCH p.productOptions po " +
            "LEFT JOIN FETCH po.option " +
            "WHERE p.no = :no")
    Optional<Product> findByNo(@Param("no") Long no);

    @Override
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    // 일반유저 상품 조회
    @Query("SELECT p FROM Product p WHERE (p.category.no = :categoryNo OR p.category.parentCategory.no = :categoryNo) AND p.isEnabled = true")
    Page<Product> findProductsForUser(@Param("categoryNo") Integer categoryNo, Pageable pageable);

    // 관리자 상품 조회(enabled = false 포함)
    @Query("SELECT p FROM Product p WHERE p.category.no = :categoryNo OR p.category.parentCategory.no = :categoryNo")
    Page<Product> findProductsForAdmin(@Param("categoryNo") Integer categoryNo, Pageable pageable);


}

