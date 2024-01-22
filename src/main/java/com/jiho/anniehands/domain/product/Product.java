package com.jiho.anniehands.domain.product;

import com.jiho.anniehands.domain.category.Category;
import com.jiho.anniehands.domain.image.Image;
import com.jiho.anniehands.domain.productoptions.ProductOptions;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"category", "images"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "thumbnail_path", nullable = false, length = 100)
    private String thumbnailPath;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "content", nullable = false, length = 255)
    private String content;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "sale")
    private Integer sale;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "is_Enabled", nullable = false)
    private Boolean isEnabled;

    // 연관 관계 매핑 (Category 엔티티와의 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductOptions> productOptions = new HashSet<>();

    @Builder
    public Product(Long no, String thumbnailPath, String name, String content, Integer price, Integer sale, Integer stock,
                   LocalDateTime createdDate, LocalDateTime updatedDate, Boolean isEnabled, Category category) {
        this.no = no;
        this.thumbnailPath = thumbnailPath;
        this.name = name;
        this.content = content;
        this.price = price;
        this.sale = sale;
        this.stock = stock;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isEnabled = isEnabled;
        this.category = category;
    }
}
