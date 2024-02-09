package com.jiho.anniehands.domain.admin.product;

import com.jiho.anniehands.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    // 관리자 상품 조회(enabled = false 포함)
    @Query("SELECT p FROM Product p WHERE p.category.no = :categoryNo OR p.category.parentCategory.no = :categoryNo")
    Page<Product> findProductsForAdmin(@Param("categoryNo") Integer categoryNo, Pageable pageable);


}
