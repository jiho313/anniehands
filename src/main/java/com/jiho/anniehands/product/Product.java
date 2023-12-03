package com.jiho.anniehands.product;

import com.jiho.anniehands.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"category"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false, length = 100)
    private String thumbnailPath;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(nullable = false)
    private Integer price;

    @Column
    private Integer sale;

    @Column(nullable = false)
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
