package com.jiho.anniehands.domain.admin;

import com.jiho.anniehands.common.exception.CustomErrorCode;
import com.jiho.anniehands.common.exception.PageException;
import com.jiho.anniehands.domain.category.Category;
import com.jiho.anniehands.domain.category.CategoryHelper;
import com.jiho.anniehands.domain.category.CategoryRepository;
import com.jiho.anniehands.domain.category.dto.CategoryDto;
import com.jiho.anniehands.domain.category.dto.CategoryResultAdmin;
import com.jiho.anniehands.domain.product.Product;
import com.jiho.anniehands.domain.product.ProductRepository;
import com.jiho.anniehands.domain.product.dto.ProductAdminDto;
import com.jiho.anniehands.domain.product.dto.ProductDto;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryHelper categoryHelper;
    private final ProductRepository productRepository;

    @Value("${s3.path.products}")
    private String s3ProductsPath;

    @Transactional
    public CategoryResultAdmin getCategoryInfoForAdmin(Integer categoryNo, Pageable pageable) {
        if (categoryNo == null) {
            return handleNullCategory(pageable);
        } else {
            return handleNonNullCategory(categoryNo, pageable);
        }
    }

    public CategoryResultAdmin searchProducts(Integer categoryNo, String opt, String keyword, Pageable pageable) {
        Specification<Product> spec = createProductSpecification(categoryNo, opt, keyword);
        Page<ProductAdminDto> productDtos = productRepository.findAll(spec, pageable)
                .map(product -> ProductAdminDto.createDto(product, s3ProductsPath));

        // 여기서는 카테고리 정보를 가져오거나, 해당되는 카테고리가 없는 경우 기본 정보를 설정합니다.
        // 예를 들어, categoryNo가 null이면 "모든 카테고리"와 같은 기본 정보를 설정할 수 있습니다.
        Category currentCategory = null;
        if (categoryNo != null) {
            currentCategory = categoryRepository.findByNo(categoryNo)
                    .orElse(null); // 카테고리가 없는 경우 null 처리
        }

        return createCategoryResultAdmin(currentCategory, productDtos);
    }

    private CategoryResultAdmin createCategoryResultAdmin(Category category, Page<ProductAdminDto> productDtos) {
        if (category == null) {
            return new CategoryResultAdmin(null, "모든 카테고리", null, null, null, productDtos);
        } else {
            // 카테고리와 관련된 추가 정보를 설정합니다.
            List<CategoryDto> relatedCategories = categoryHelper.getRelatedCategories(category);
            return new CategoryResultAdmin(category.getNo(), category.getName(),
                    category.getParentCategory() != null ? category.getParentCategory().getNo() : null,
                    category.getParentCategory() != null ? category.getParentCategory().getName() : null,
                    relatedCategories, productDtos);
        }
    }

    private Specification<Product> createProductSpecification(Integer categoryNo, String opt, String keyword) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (categoryNo != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), categoryNo));
            }

            if (StringUtils.hasText(opt) && StringUtils.hasText(keyword)) {
                // 'opt'에 따라 다른 필드를 검색할 수 있도록 조건을 추가합니다.
                // 예: opt가 'name'일 경우, name 필드에서 keyword를 검색합니다.
                predicates.add(criteriaBuilder.like(root.get(opt), "%" + keyword + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    private CategoryResultAdmin handleNullCategory(Pageable pageable) {
        List<CategoryDto> relatedCategories = fineGetParentCategories();
        Page<ProductAdminDto> productDtos = productRepository.findAll(pageable)
                .map(product -> ProductAdminDto.createDto(product, s3ProductsPath));

        return new CategoryResultAdmin(null, "모든 상품 목록", relatedCategories, productDtos);
    }

    private CategoryResultAdmin handleNonNullCategory(Integer categoryNo, Pageable pageable) {
        Category currentCategory = categoryRepository.findByNo(categoryNo)
                .orElseThrow(() -> new PageException(CustomErrorCode.NOT_FOUND));

        List<CategoryDto> relatedCategories = categoryHelper.getRelatedCategories(currentCategory);
        Page<ProductAdminDto> productDtos = productRepository.findProductsForAdmin(categoryNo, pageable)
                .map(product -> ProductAdminDto.createDto(product, s3ProductsPath));

        return getCategoryResultForAdmin(currentCategory, relatedCategories, productDtos);
    }

    private CategoryResultAdmin getCategoryResultForAdmin(Category currentCategory, List<CategoryDto> relatedCategories, Page<ProductAdminDto> productDtos) {
        if (currentCategory.getParentCategory() == null) {
            return new CategoryResultAdmin(currentCategory.getNo(), currentCategory.getName(), relatedCategories, productDtos);
        } else {
            return new CategoryResultAdmin(currentCategory.getNo(), currentCategory.getName(),
                    currentCategory.getParentCategory().getNo(), currentCategory.getParentCategory().getName(),
                    relatedCategories, productDtos);
        }
    }

    public List<CategoryDto> fineGetParentCategories() {
        return categoryRepository.findTopLevelCategories().stream()
                .map(CategoryDto::createDto).toList();
    }
}
