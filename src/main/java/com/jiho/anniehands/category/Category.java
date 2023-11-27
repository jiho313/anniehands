package com.jiho.anniehands.category;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"parentCategory", "childCategories"})
public class Category {

    @Id
    private Integer no;

    @Column(nullable = false, length = 45)
    private String name;

    // 부모 카테고리와의 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", insertable = false, updatable = false)
    private Category parentCategory;

    // 자식 카테고리 리스트를 위한 관계 매핑
    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    private List<Category> childCategories;

    @Builder
    public Category(Integer no, String name, Category parentCategory, List<Category> childCategories) {
        this.no = no;
        this.name = name;
        this.parentCategory = parentCategory;
        this.childCategories = childCategories;
    }
}
