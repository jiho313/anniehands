package com.jiho.anniehands;

import com.jiho.anniehands.product.Product;
import com.jiho.anniehands.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnniehandsApplicationTests {

	@Autowired
	private ProductService productService;

	@Test
	void getProductByNo() {
		Product product = productService.getProductByNo(19L);

		assertThat(product.getName()).isEqualTo("103인기상품에 올라갈 테스트 상품");
		System.out.println("product = " + product);
	}

	@Test
	void get5NewProducts() {
		List<Product> products = productService.getTop5NewProducts();
		assertThat(products.size()).isEqualTo(5);
	}

}
