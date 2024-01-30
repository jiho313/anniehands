package com.jiho.anniehands;

import com.jiho.anniehands.common.exception.CustomErrorCode;
import com.jiho.anniehands.domain.product.ProductService;
import com.jiho.anniehands.domain.user.Role;
import com.jiho.anniehands.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnniehandsApplicationTests {

	@Autowired
	private ProductService productService;
	@Autowired
	private UserRepository userRepository;

	@Test
	void enumTest() {
		assertThat(Role.ROLE_ADMIN.name()).isEqualTo("ROLE_ADMIN");
		assertThat(CustomErrorCode.NO_MATCHING_MEMBER.getCode()).isEqualTo("400");
		assertThat(CustomErrorCode.NO_MATCHING_MEMBER.getMessage()).isEqualTo("없는 회원입니다.");
	}
}
