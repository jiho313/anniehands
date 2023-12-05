package com.jiho.anniehands;

import com.jiho.anniehands.user.Role;
import com.jiho.anniehands.product.Product;
import com.jiho.anniehands.product.ProductService;
import com.jiho.anniehands.user.User;
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
		Product product = productService.getProductByNo(19L);

		assertThat(product.getName()).isEqualTo("103인기상품에 올라갈 테스트 상품");
		System.out.println("product = " + product);
	}

	@Test
	void get5NewProducts() {
		List<Product> products = productService.getTop5NewProducts();
		assertThat(products).hasSize(5);
	}

	@Test
	void roleEnumTest() {
		User user = User.builder().role(Role.ROLE_USER).build();
		assertThat(user.getRole().name()).isEqualTo("ROLE_USER");
	}

}
