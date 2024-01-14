package com.jiho.anniehands;

import com.jiho.anniehands.common.exception.CustomErrorCode;
import com.jiho.anniehands.product.Product;
import com.jiho.anniehands.product.ProductDto;
import com.jiho.anniehands.product.ProductService;
import com.jiho.anniehands.user.Role;
import com.jiho.anniehands.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnniehandsApplicationTests {

	@Autowired
	private ProductService productService;
	@Autowired
	private UserRepository userRepository;

	@Test
	void getProductByNo() {
		ProductDto product = productService.getProductByNo(19L);

		assertThat(product.getName()).isEqualTo("103 건강보조제!");
		System.out.println("product = " + product);
	}

	@Test
	void get5NewProducts() {
		List<ProductDto> products = productService.getTop5NewProducts();
		assertThat(products).hasSize(5);
	}

	@Test
	void enumTest() {
		assertThat(Role.ROLE_ADMIN.name()).isEqualTo("ROLE_ADMIN");
		assertThat(CustomErrorCode.NO_MATCHING_MEMBER.getCode()).isEqualTo("400");
		assertThat(CustomErrorCode.NO_MATCHING_MEMBER.getMessage()).isEqualTo("없는 회원입니다.");
	}
}
