package com.jiho.anniehands.domain.category;

import com.jiho.anniehands.common.exception.CustomErrorCode;
import com.jiho.anniehands.common.exception.PageException;
import com.jiho.anniehands.domain.product.Product;
import com.jiho.anniehands.domain.product.ProductDto;
import com.jiho.anniehands.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @Value("${s3.path.products}")
    private String s3ProductsPath;

    @Transactional
    public CategoryResult getCategoryInfo(Integer categoryNo, Pageable pageable) {
        Category currentCategory = categoryRepository.findByNo(categoryNo)
                .orElseThrow(() -> new PageException(CustomErrorCode.NOT_FOUND));
        List<CategoryDto> relatedCategories = getRelatedCategories(currentCategory);
        Page<ProductDto> productDtos = productRepository.findByCategoryNo(categoryNo, pageable)
                .map(product -> ProductDto.createDto(product, s3ProductsPath));
        return new CategoryResult(currentCategory.getNo(), currentCategory.getName(), relatedCategories, productDtos);
    }

    private List<CategoryDto> getRelatedCategories(Category category) {
        Integer parentNo = category.getParentCategory() != null ? category.getParentCategory().getNo() : category.getNo();
        return categoryRepository.findByParentCategory_No(parentNo).stream()
                .map(CategoryDto::createDto)
                .toList();
    }
}
