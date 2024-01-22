package com.jiho.anniehands.domain.category;

import lombok.*;

@Getter
@ToString
@Builder
public class CategoryDto {

    private Integer no;
    private String name;
    private Integer parent;

    public static CategoryDto createDto(Category category) {
        return CategoryDto.builder()
                .no(category.getNo())
                .name(category.getName())
                .parent(category.getParentCategory().getNo())
                .build();
    }
}
