package com.jiho.anniehands.domain.category;

import com.jiho.anniehands.domain.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryResult {

    private Integer no;
    private String name;
    private List<CategoryDto> relatedCategories;
    private Page<ProductDto> products;

}
