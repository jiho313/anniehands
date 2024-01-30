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

    public CategoryResultAdmin searchProducts(String opt, String keyword, Pageable pageable) {
        Specification<Product> spec = createProductSpecification(opt, keyword);
        Page<ProductAdminDto> productDtos = productRepository.findAll(spec, pageable)
                .map(product -> ProductAdminDto.createDto(product, s3ProductsPath));

        return new CategoryResultAdmin(null, "상품 검색 결과", null, productDtos);
    }

    private Specification<Product> createProductSpecification(String opt, String keyword) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(opt) && StringUtils.hasText(keyword)) {
                if ("no".equals(opt)) {
                    Long keywordAsLong = Long.parseLong(keyword);
                    predicates.add(criteriaBuilder.equal(root.get(opt), keywordAsLong));
                } else {
                    // opt가 'name'과 같은 문자열 타입 필드인 경우
                    predicates.add(criteriaBuilder.like(root.get(opt), "%" + keyword + "%"));
                }
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
