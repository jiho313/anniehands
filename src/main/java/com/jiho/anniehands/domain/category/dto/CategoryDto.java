
package com.jiho.anniehands.domain.category.dto;

import com.jiho.anniehands.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class CategoryDto {

    private Integer no;
    private String name;

    public static CategoryDto createDto(Category category) {
        return CategoryDto.builder()
                .no(category.getNo())
                .name(category.getName())
                .build();
    }

}
