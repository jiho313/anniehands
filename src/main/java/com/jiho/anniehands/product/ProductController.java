package com.jiho.anniehands.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {

    @GetMapping("/list")
    public String list(Model model) {
        return "page/product/list";
    }
}
