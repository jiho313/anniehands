package com.jiho.anniehands.main;

import com.jiho.anniehands.product.Product;
import com.jiho.anniehands.product.ProductDto;
import com.jiho.anniehands.product.ProductService;
import com.jiho.anniehands.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomUserDetails loginUser, Model model) {
        // TODO: 현재 인기상품은 더미 데이터 사용 중 적절한 인기 제품 기준 할당 후 뷰에 출력하기
        List<ProductDto> products = productService.getTop5NewProducts();
        model.addAttribute("products", products);
        if (loginUser != null) {
            log.info("현재 로그인된 유저 ====> {}", loginUser.toString());
        }
        return "page/main/home";
    }

}
