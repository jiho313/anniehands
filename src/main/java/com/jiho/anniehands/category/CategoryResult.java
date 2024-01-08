package com.jiho.anniehands.category;

import com.jiho.anniehands.product.ProductDto;
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
    private Integer parent;
    private List<CategoryDto> children;
    private Page<ProductDto> products;

    public void setChildren(List<CategoryDto> children) {
        this.children = children;
    }
}
