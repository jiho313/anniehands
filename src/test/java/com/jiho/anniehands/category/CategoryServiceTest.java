package com.jiho.anniehands.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

//    @Test
//    public void testGetCategoryInfoFirstMethod() {
//        Integer categoryNo = 100; // 테스트용 카테고리 번호
//        Pageable pageable = PageRequest.of(0, 20);
//
//        // 첫 번째 방법 테스트
//        long startTime = System.currentTimeMillis();
//        categoryService.getCategoryInfo(categoryNo, pageable);
//        long endTime = System.currentTimeMillis();
//        System.out.println("First Method Execution Time: " + (endTime - startTime) + " ms");
//    }

//    @Test
//    public void testGetCategoryInfoSecondMethod() {
//        Integer categoryNo = 100; // 테스트용 카테고리 번호
//        Pageable pageable = PageRequest.of(0, 20);
//
//        // 두 번째 방법 테스트
//        long startTime = System.currentTimeMillis();
//        categoryService.getCategorySecondInfo(categoryNo, pageable);
//        long endTime = System.currentTimeMillis();
//        System.out.println("Second Method Execution Time: " + (endTime - startTime) + " ms");
//    }

}