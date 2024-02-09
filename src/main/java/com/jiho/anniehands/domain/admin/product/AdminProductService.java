package com.jiho.anniehands.domain.admin.product;

import com.jiho.anniehands.domain.category.Category;
import com.jiho.anniehands.domain.category.CategoryRepository;
import com.jiho.anniehands.domain.image.Image;
import com.jiho.anniehands.domain.options.Options;
import com.jiho.anniehands.domain.options.OptionsRepository;
import com.jiho.anniehands.domain.product.Product;
import com.jiho.anniehands.domain.product.ProductRepository;
import com.jiho.anniehands.domain.product.dto.ProductCreateForm;
import com.jiho.anniehands.domain.productoption.ProductOption;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionsRepository optionsRepository;

    public void createProduct(ProductCreateForm form) {
        Category category = categoryRepository.findByNo(form.getCategoryNo())
                .orElseThrow(EntityNotFoundException::new);
        Product productEntity = form.toProductEntity(category);

        // 이미지 처리
        String[] originFileName = form.getOriginFileName();
        String[] serverFileName = form.getServerFileName();
        Long[] fileSize = form.getFileSize();
        for (int i = 0; i < serverFileName.length; i++) {
            Image image = Image.builder()
                    .product(productEntity)
                    .serverName(serverFileName[i])
                    .originName(originFileName[i])
                    .fileSize(fileSize[i]).build();
            productEntity.addProductImage(image);
        }
        // 색상 옵션 처리
        if (form.getColorOptions() != null) {
            for (Integer colorOptionNo : form.getColorOptions()) {
                Options colorOption = optionsRepository.findByNo(colorOptionNo)
                        .orElseThrow(() -> new EntityNotFoundException("Color option not found"));
                ProductOption productColorOption = ProductOption.builder()
                        .product(productEntity)
                        .option(colorOption)
                        .build();
                productEntity.addProductOption(productColorOption);
            }
        }
        // 사이즈 옵션 처리
        if (form.getSizeOptions() != null) {
            for (Integer sizeOptionNo : form.getSizeOptions()) {
                Options sizeOption = optionsRepository.findByNo(sizeOptionNo)
                        .orElseThrow(() -> new EntityNotFoundException("Size option not found"));
                ProductOption productSizeOption = ProductOption.builder()
                        .product(productEntity)
                        .option(sizeOption)
                        .build();
                productEntity.addProductOption(productSizeOption);
            }
        }
        log.info("엔티티 정보 ====== > {}", productEntity);
        productRepository.save(productEntity);
    }
}
