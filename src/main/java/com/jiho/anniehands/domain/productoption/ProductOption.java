package com.jiho.anniehands.domain.productoption;

import com.jiho.anniehands.domain.options.Options;
import com.jiho.anniehands.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "option_no", nullable = false)
    private Options option;

    public ProductOption(Product product, Options option) {
        this.product = product;
        this.option = option;
    }

}
