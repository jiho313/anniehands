package com.jiho.anniehands.main;

import com.jiho.anniehands.product.Product;
import com.jiho.anniehands.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        List<Product> products = this.productService.getTop5NewProducts();
        model.addAttribute("products", products);
        log.info("상품들 ====> {}", products);
        return "page/main/home";
    }

}
