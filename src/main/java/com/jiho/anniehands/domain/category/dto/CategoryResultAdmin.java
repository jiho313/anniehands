package com.jiho.anniehands.domain.category.dto;

import com.jiho.anniehands.domain.product.dto.ProductAdminDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class CategoryResultAdmin {

    private Integer no;
    private String name;
    private Integer parentNo;
    private String parentName;
    private List<CategoryDto> relatedCategories;
    private Page<ProductAdminDto> products;

    public CategoryResultAdmin(Integer no, String name, List<CategoryDto> relatedCategories, Page<ProductAdminDto> products) {
        this.no = no;
        this.name = name;
        this.relatedCategories = relatedCategories;
        this.products = products;
    }

    public CategoryResultAdmin(Integer no, String name, Integer parentNo, String parentName, List<CategoryDto> relatedCategories, Page<ProductAdminDto> products) {
        this.no = no;
        this.name = name;
        this.parentNo = parentNo;
        this.parentName = parentName;
        this.relatedCategories = relatedCategories;
        this.products = products;
    }
}