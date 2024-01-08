package com.jiho.anniehands.category;

import com.jiho.anniehands.common.exception.CustomErrorCode;
import com.jiho.anniehands.common.exception.PageException;
import com.jiho.anniehands.product.ProductDto;
import com.jiho.anniehands.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Optional<Category> findByNo(Integer categoryNo) {
        return categoryRepository.findByNo(categoryNo);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public CategoryResult getCategoryInfo(Integer categoryNo, Pageable pageable) {
        // 2. 카테고리와 상품은 양방향 매핑으로 카테고리로 번호로 상품 조회가 가능하다.
        // DB접근의 횟수가 적어짐 상대적으로 수행처리가 속도가 빠름
        List<Category> categories = categoryRepository.findCategoriesAndSubcategoriesByNo(categoryNo);
        if (categories.isEmpty()) {
            throw new PageException(CustomErrorCode.NOT_FOUND);
        }
        Category currentCategory = categories.stream().filter(c -> c.getNo().equals(categoryNo)).findFirst().orElse(null);
        List<CategoryDto> childCategories = categories.stream()
                .filter(c -> !c.getNo().equals(categoryNo))
                .map(CategoryDto::createDto).toList();
        Page<ProductDto> productDtos = productRepository.findByCategoryNo(categoryNo, pageable)
                .map(ProductDto::createDto);

        Integer parentNo = currentCategory.getParentCategory() != null ?
                currentCategory.getParentCategory().getNo() : null;
        return new CategoryResult(currentCategory.getNo(), currentCategory.getName(), parentNo, childCategories, productDtos);
    }

//    @Transactional
//    public CategoryResult getCategoryInfo(Integer categoryNo, Pageable pageable) {
////        1. DB접근 횟수가 한 번 더 많지만 가독성이 좋음
//        Category currentCategory = categoryRepository.findByNo(categoryNo)
//                .orElseThrow(() -> new PageException(CustomErrorCode.NOT_FOUND));
//        List<CategoryDto> childCategories = categoryRepository.findByParentCategory_No(categoryNo).stream()
//                .map(CategoryDto::createDto).toList();
//        Page<ProductDto> productDtos = productRepository.findByCategoryNo(categoryNo, pageable)
//                .map(ProductDto::createDto);
//
//        Integer parentNo = currentCategory.getParentCategory() != null ?
//                currentCategory.getParentCategory().getNo() : null;
//        return new CategoryResult(currentCategory.getNo(), currentCategory.getName(), parentNo, childCategories, productDtos);
//    }

    // 상위 카테고리 번호로 하위 카테고리 조회
    public List<CategoryDto> findSubcategories(Integer parentNo) {
        return categoryRepository.findByParentCategory_No(parentNo).stream()
                .map(CategoryDto::createDto).toList();
    }
}
