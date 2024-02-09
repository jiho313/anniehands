package com.jiho.anniehands.domain.product.dto;

import com.jiho.anniehands.domain.category.Category;
import com.jiho.anniehands.domain.product.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductCreateForm {

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    @Size(max = 50, message = "상품명은 50자를 넘길 수 없습니다.")
    private String name;

    @NotBlank(message = "상품 설명은 필수 입력 값입니다.")
    @Size(max = 1000, message = "상품 설명은 1000자를 넘길 수 없습니다.")
    private String content;

    @NotNull(message = "최소 가격은 10원 이상입니다.")
    private Integer price;

    private Integer sale;

    @NotNull(message = "최소 재고는 1개 이상입니다.")
    private Integer stock;

    @NotNull(message = "카테고리는 필수 입력 값입니다.")
    private Integer categoryNo;

    private Integer[] colorOptions;

    private Integer[] sizeOptions;

    private String[] originFileName;

    private String[] serverFileName;

    private Long[] fileSize;

    public Product toProductEntity(Category category) {
        String thumbnailPath = this.getServerFileName()[0];
        Integer saveSale = this.getSale();
        if (saveSale == null) {
            saveSale = this.getPrice();
        }
        return Product.builder()
                .thumbnailPath(thumbnailPath)
                .name(this.getName())
                .content(this.getContent())
                .price(this.getPrice())
                .sale(saveSale)
                .stock(this.getStock())
                .category(category)
                .build();
    }

}
