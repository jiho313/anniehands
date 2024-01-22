package com.jiho.anniehands.domain.product;

import com.jiho.anniehands.domain.image.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    @Test
    void testFindProductByNo() {
        Long productId = 3L; // 테스트할 상품 ID
        Optional<Product> product = productRepository.findByNo(productId);
        List<Image> images = product.get().getImages();
        for (Image image : images) {
            System.out.println("image.getOriginName() = " + image.getOriginName());
        }
    }

    @Transactional
    @Test
    void testGetProductWithImages() {
        Long productId = 3L; // 테스트할 상품 ID
        Product product = productRepository.findProductWithImages(productId);
        List<Image> images = product.getImages();
        for (Image image : images) {
            System.out.println("image.getOriginName() = " + image.getOriginName());
        }
    }

}