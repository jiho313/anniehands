package com.jiho.anniehands.domain.product;

import com.jiho.anniehands.common.util.BaseTimeEntity;
import com.jiho.anniehands.domain.category.Category;
import com.jiho.anniehands.domain.image.Image;
import com.jiho.anniehands.domain.productoption.ProductOption;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"category", "images"})
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "thumbnail_path", nullable = false, length = 100)
    private String thumbnailPath;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "sale")
    private Integer sale;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "is_Enabled", nullable = false)
    private Boolean isEnabled;

    // 연관 관계 매핑 (Category 엔티티와의 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductOption> productOptions = new HashSet<>();

    @Builder
    public Product(String thumbnailPath, String name, String content, Integer price, Integer sale,
                   Integer stock, Boolean isEnabled, Category category) {
        this.thumbnailPath = thumbnailPath;
        this.name = name;
        this.content = content;
        this.price = price;
        this.sale = sale;
        this.stock = stock;
        this.isEnabled = isEnabled;
        this.category = category;
    }

    // ProductOption 추가 메소드 (옵셔널)
    public void addProductOption(ProductOption productOption) {
        this.productOptions.add(productOption);
        productOption.setProduct(this);
    }

    public void addProductImage(Image image) {
        this.images.add(image);
        image.setProduct(this);
    }

}
