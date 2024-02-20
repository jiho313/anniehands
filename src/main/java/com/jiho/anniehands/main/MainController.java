package com.jiho.anniehands.main;

import com.jiho.anniehands.global.exception.CustomErrorCode;
import com.jiho.anniehands.global.exception.PageException;
import com.jiho.anniehands.domain.product.ProductService;
import com.jiho.anniehands.domain.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model, @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        // TODO: 현재 인기상품은 더미 데이터 사용 중 적절한 인기 제품 기준 할당 후 뷰에 출력하기
        Page<ProductDto> products = productService.getTop5NewProducts(pageable);
        model.addAttribute("products", products);
        return "page/main/home";
    }

    @GetMapping("/incomplete-url")
    public void incompleteUrl() {
        throw new PageException(CustomErrorCode.INCOMPLETE_PAGE);
    }

}
