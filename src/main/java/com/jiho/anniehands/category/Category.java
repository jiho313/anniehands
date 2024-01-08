package com.jiho.anniehands.category;

import com.jiho.anniehands.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    // 상품 목록과 관계 매핑
    // mappedBy는 연관관계의 주인이 아닌 쪽에서 사용되며, 연관관계의 주인을 지정할 때 사용한다.
    //  연관관계의 주인은 외래키를 관리하는 쪽(category)이다.
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    // 부모 카테고리와의 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_no")
    private Category parentCategory;

    // 자식 카테고리 리스트를 위한 관계 매핑
    // mappedBy는 연관관계의 주인이 아닌 쪽에서 사용되며, 연관관계의 주인을 지정할 때 사용한다.
    //  연관관계의 주인은 외래키를 관리하는 쪽(parentCategory)이다.
    @OneToMany(mappedBy = "parentCategory")
    private List<Category> childCategories = new ArrayList<>();

    @Builder
    public Category(Integer no, String name, Category parentCategory, List<Category> childCategories) {
        this.no = no;
        this.name = name;
        this.parentCategory = parentCategory;
        this.childCategories = childCategories;
    }
}
