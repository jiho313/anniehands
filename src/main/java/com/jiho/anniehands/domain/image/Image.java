package com.jiho.anniehands.domain.image;

import com.jiho.anniehands.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @Column(name = "origin_name", nullable = false, length = 300)
    private String originName;

    @Column(name = "server_name", nullable = false, length = 300)
    private String serverName;

    @Column(name = "file_size", nullable = false)
    private Integer fileSize;

    @Builder
    public Image(Long no, Product product, String originName, String serverName, Integer fileSize) {
        this.no = no;
        this.product = product;
        this.originName = originName;
        this.serverName = serverName;
        this.fileSize = fileSize;
    }
}
