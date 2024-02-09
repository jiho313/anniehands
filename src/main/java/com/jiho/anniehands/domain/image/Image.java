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
    private Long fileSize;

    public void setProduct(Product product) {
        this.product = product;
    }

    @Builder
    public Image(Product product, String originName, String serverName, Long fileSize) {
        this.product = product;
        this.originName = originName;
        this.serverName = serverName;
        this.fileSize = fileSize;
    }

}
