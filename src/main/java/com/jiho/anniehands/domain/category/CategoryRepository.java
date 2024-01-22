package com.jiho.anniehands.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Optional<Category> findByNo(Integer no);

    // 상위 카테고리 번호를 기반으로 하위 카테고리 목록을 조회하는 메서드
    List<Category> findByParentCategory_No(Integer parentNo);

    // 1 + N 테스트 사용
    List<Category> findAll();

    // 1 + N 테스트 사용
    @Query("SELECT c FROM Category c JOIN FETCH c.products")
    List<Category> findAllWithProducts();

//    @Query("SELECT c FROM Category c LEFT JOIN c.parentCategory p WHERE c.no = :categoryNo OR p.no = :categoryNo")
//    List<Category> findCategoriesAndSubcategoriesByNo(Integer categoryNo);

}
