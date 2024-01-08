package com.jiho.anniehands.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Optional<Category> findByNo(Integer No);

    List<Category> findAll();

    // 상위 카테고리 번호를 기반으로 하위 카테고리 목록을 조회하는 메서드
    List<Category> findByParentCategory_No(Integer parentNo);

    @Query("SELECT c FROM Category c LEFT JOIN c.products p ON p.isEnabled = true WHERE c.no = :categoryNo OR c.parentCategory.no = :categoryNo")
    List<Category> findCategoriesAndSubcategoriesByNo(Integer categoryNo);

}
