//package com.jiho.anniehands.domain.category;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@SpringBootTest
//class CategoryRepositoryTest {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Test
//    @DisplayName("1+N 테스트1")
//    @Transactional
//    void onePlusTest1() {
//        System.out.println("=========== 모두 찾기 시작 ===========");
//        List<Category> categories = categoryRepository.findAll();
//        System.out.println("=========== 모두 찾기 끝 ===========");
//        int index= 1;
//        for (Category category : categories) {
//            // 이 부분에서 각 Category의 Product 리스트에 접근
//            System.out.println("=========== 카테고리가 가진 상품 찾기 시작 "+ index+ "번 ===========");
//            int productsSize = category.getProducts().size();
//            System.out.println("=========== 카테고리가 가진 상품 찾기 끝 ===========");
//            index++;
//        }
//    }
//    @Test
//    @DisplayName("1+N 테스트2")
//    @Transactional
//    void onePlusTest2() {
//        System.out.println("=========== 모두 찾기 시작 ===========");
//        List<Category> categories = categoryRepository.findAllWithProducts();
//        System.out.println("=========== 모두 찾기 끝 ===========");
//        int index= 1;
//        for (Category category : categories) {
//            // 이 부분에서 각 Category의 Product 리스트에 접근
//            System.out.println("=========== 카테고리가 가진 상품 찾기 시작 "+ index+ "번 ===========");
//            int productsSize = category.getProducts().size();
//            System.out.println("=========== 카테고리가 가진 상품 찾기 끝 ===========");
//            index++;
//        }
//    }
//
//}