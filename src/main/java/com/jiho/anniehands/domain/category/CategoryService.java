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
//        1. DB접근 횟수가 한 번 더 많지만 가독성이 좋음
        Category currentCategory = categoryRepository.findByNo(categoryNo)
                .orElseThrow(() -> new PageException(CustomErrorCode.NOT_FOUND));
        List<CategoryDto> childCategories = categoryRepository.findByParentCategory_No(categoryNo).stream()
                .map(CategoryDto::createDto).toList();
        Page<ProductDto> productDtos = productRepository.findByCategoryNo(categoryNo, pageable)
                .map((Product product) -> ProductDto.createDto(product, s3ProductsPath));

        Integer parentNo = currentCategory.getParentCategory() != null ?
                currentCategory.getParentCategory().getNo() : null;
        return new CategoryResult(currentCategory.getNo(), currentCategory.getName(), parentNo, childCategories, productDtos);
    }

    // 상위 카테고리 번호로 하위 카테고리 조회
    public List<CategoryDto> findSubcategories(Integer parentNo) {
        return categoryRepository.findByParentCategory_No(parentNo).stream()
                .map(CategoryDto::createDto).toList();
    }

//    @Transactional
//    public CategoryResult getCategorySecondInfo(Integer categoryNo, Pageable pageable) {
//        // 2. 카테고리와 상품은 양방향 매핑으로 카테고리로 번호로 상품 조회가 가능하다.
//        // 첫번째 방법에 비해 DB접근 횟수 1회 적어짐
//        List<Category> categories = categoryRepository.findCategoriesAndSubcategoriesByNo(categoryNo);
//        if (categories.isEmpty()) {
//            throw new PageException(CustomErrorCode.NOT_FOUND);
//        }
//        // categoryNo과 일치하는 것을 찾고 없으면 null을 반환
//        Category currentCategory = categories.stream().filter(c -> c.getNo().equals(categoryNo)).findFirst().orElse(null);
//        // categoryNo을 제외하고 나머지 카테고리 맵핑, 부모 카테고리 요청일 경우 ParentCategory null 방지를 위함
//        List<CategoryDto> childCategories = categories.stream()
//                .filter(c -> !c.getNo().equals(categoryNo))
//                .map(CategoryDto::createDto).toList();
//        Page<ProductDto> productDtos = productRepository.findByCategoryNo(categoryNo, pageable)
//                .map((Product product) -> ProductDto.createDto(product, s3BasePath));
//        Integer parentNo = currentCategory.getParentCategory() != null ?
//                currentCategory.getParentCategory().getNo() : null;
//        return new CategoryResult(currentCategory.getNo(), currentCategory.getName(), parentNo, childCategories, productDtos);
//    }
}
