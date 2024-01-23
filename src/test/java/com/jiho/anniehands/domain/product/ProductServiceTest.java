package com.jiho.anniehands.domain.product;

import com.jiho.anniehands.domain.image.Image;
import com.jiho.anniehands.domain.productoptions.ProductOptions;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("1+N 쿼리 튜닝 테스트")
    void testFindProductByNo() {
        Long productId = 3L; // 테스트할 상품 ID
        Optional<Product> product = productRepository.findByNo(productId);

        List<Image> images = product.get().getImages();
        for (Image image : images) {
            System.out.println("image.getOriginName() = " + image.getOriginName());
        }

        Set<ProductOptions> productOptions = product.get().getProductOptions();
        for (ProductOptions options : productOptions) {
            System.out.println("options.getOption().getValue() = " + options.getOption().getValue());
        }
        
        
    }
    
}