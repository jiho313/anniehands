package com.jiho.anniehands.domain.productoptions;

import com.jiho.anniehands.domain.option.Options;
import com.jiho.anniehands.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "option_no", nullable = false)
    private Options option;

}
