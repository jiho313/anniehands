package com.jiho.anniehands.domain.admin.product;

import com.jiho.anniehands.global.exception.CustomErrorCode;
import com.jiho.anniehands.global.exception.PageException;
import com.jiho.anniehands.global.common.file.FileService;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionsRepository optionsRepository;
    private final AdminProductJdbcRepository adminProductJdbcRepository;
    private final FileService fileService;

    @Transactional
    public void createProduct(ProductCreateForm form) {
        Category category = categoryRepository.findByNo(form.getCategoryNo())
                .orElseThrow(() -> new EntityNotFoundException("해당 카테고리를 찾을 수 없습니다."));
        Product productEntity = form.toProductEntity(category);
        productRepository.save(productEntity);
        // 이미지 처리
        List<Image> productImages = processImages(form, productEntity);
        adminProductJdbcRepository.bulkInsertImages(productImages);
        // 옵션 처리
        if (form.getColorOptions() != null || form.getSizeOptions() != null) {
            List<ProductOption> productOptions = processOption(form, productEntity);
            adminProductJdbcRepository.bulkInsertProductOptions(productOptions);
        }
    }

    @NotNull
    private List<ProductOption> processOption(ProductCreateForm form, Product productEntity) {
        List<ProductOption> productOptions = new ArrayList<>();
        if (form.getColorOptions() != null) {
            processEachOption(form.getColorOptions(), productOptions, "Color option not found", productEntity);
        }
        if (form.getSizeOptions() != null) {
            processEachOption(form.getSizeOptions(), productOptions, "Size option not found", productEntity);
        }
        return productOptions;
    }

    private void processEachOption(Integer[] optionNos, List<ProductOption> productOptions, String errorMessage, Product productEntity) {
        if (optionNos != null) {
            for (Integer optionNo : optionNos) {
                Options option = optionsRepository.findByNo(optionNo)
                        .orElseThrow(() -> new EntityNotFoundException(errorMessage));
                productOptions.add(new ProductOption(productEntity, option));
            }
        }
    }

    @NotNull
    private List<Image> processImages(ProductCreateForm form, Product productEntity) {
        String[] originFileName = form.getOriginFileName();
        String[] serverFileName = form.getServerFileName();
        Long[] fileSize = form.getFileSize();
        List<Image> productImages = new ArrayList<>();
        for (int i = 1; i < serverFileName.length; i++) {
            Image image = Image.builder()
                    .product(productEntity)
                    .serverName(serverFileName[i])
                    .originName(originFileName[i])
                    .fileSize(fileSize[i]).build();
            productImages.add(image);
        }
        return productImages;
    }

    @Transactional
    public void productDelete(Long no) {
        Product product = productRepository.findByNo(no)
                .orElseThrow(() -> new PageException(CustomErrorCode.NOT_FOUND_PRODUCT, "/admin/product/list"));
        List<String> imagePathList = new ArrayList<>(product.getImages().stream()
                .map(Image::getServerName)
                .toList());
        imagePathList.add(product.getThumbnailPath());
        if (!imagePathList.isEmpty()) {
            fileService.deleteFileByPath("product", imagePathList);
        }
        productRepository.delete(product);
    }
}
