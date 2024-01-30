package com.jiho.anniehands.domain.category;

import com.jiho.anniehands.common.exception.CustomErrorCode;
import com.jiho.anniehands.common.exception.PageException;
import com.jiho.anniehands.domain.category.dto.CategoryDto;
import com.jiho.anniehands.domain.category.dto.CategoryResult;
import com.jiho.anniehands.domain.product.ProductRepository;
import com.jiho.anniehands.domain.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryHelper categoryHelper;
    @Value("${s3.path.products}")
    private String s3ProductsPath;

    @Transactional
    public CategoryResult getCategoryInfo(Integer categoryNo, Pageable pageable) {
        Category currentCategory = categoryRepository.findByNo(categoryNo)
                .orElseThrow(() -> new PageException(CustomErrorCode.NOT_FOUND));
        List<CategoryDto> relatedCategories = categoryHelper.getRelatedCategories(currentCategory);
        Page<ProductDto> productDtos = productRepository.findProductsForUser(categoryNo, pageable)
                .map(product -> ProductDto.createDto(product, s3ProductsPath));
        return getCategoryResult(currentCategory, relatedCategories, productDtos);
    }

    // 카테고리 결과를 반환한다.
    // 현재, 부모/형제 카테고리 정보와 해당 카테고리의 상품 Page<ProductDto>가지고 있다.
    @NotNull
    private CategoryResult getCategoryResult(Category currentCategory, List<CategoryDto> relatedCategories, Page<ProductDto> productDtos) {
        if (currentCategory.getParentCategory() == null) {
            return new CategoryResult(currentCategory.getNo(), currentCategory.getName(), relatedCategories, productDtos);
        } else {
            return new CategoryResult(currentCategory.getNo(), currentCategory.getName(),
                    currentCategory.getParentCategory().getNo(), currentCategory.getParentCategory().getName(),
                    relatedCategories, productDtos);
        }
    }
}
