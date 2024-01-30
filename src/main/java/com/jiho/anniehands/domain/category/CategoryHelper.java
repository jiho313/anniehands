package com.jiho.anniehands.domain.category;

import com.jiho.anniehands.domain.category.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryHelper {

    private final CategoryRepository categoryRepository;

    // 하위 혹은 형제 카테고리를 가져온다.
    public List<CategoryDto> getRelatedCategories(Category currentCategory) {
        return categoryRepository.findByParentCategory_No(getParentCategoryNo(currentCategory)).stream()
                .map(CategoryDto::createDto)
                .toList();
    }

    // 현재 카테고리로 부모 카테고리의 번호를 반환한다.
    // 해당 카테고리가 부모면 자신의 카테고리 번호를, 자식이면 부모의 카테고리 번호를 반환한다.
    private Integer getParentCategoryNo(Category category) {
        return Optional.ofNullable(category.getParentCategory())
                .map(Category::getNo)
                .orElse(category.getNo());
    }
}
