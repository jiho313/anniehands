package com.jiho.anniehands.domain.category.dto;

import com.jiho.anniehands.domain.product.dto.ProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class CategoryResult {

    private Integer no;
    private String name;
    private Integer parentNo;
    private String parentName;
    private List<CategoryDto> relatedCategories;
    private Page<ProductDto> products;

    public CategoryResult(Integer no, String name, List<CategoryDto> relatedCategories, Page<ProductDto> products) {
        this.no = no;
        this.name = name;
        this.relatedCategories = relatedCategories;
        this.products = products;
    }

    public CategoryResult(Integer no, String name, Integer parentNo, String parentName, List<CategoryDto> relatedCategories, Page<ProductDto> products) {
        this.no = no;
        this.name = name;
        this.parentNo = parentNo;
        this.parentName = parentName;
        this.relatedCategories = relatedCategories;
        this.products = products;
    }
}
