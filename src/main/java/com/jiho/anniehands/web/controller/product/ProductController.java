package com.jiho.anniehands.web.controller.product;

import com.jiho.anniehands.domain.category.CategoryService;
import com.jiho.anniehands.domain.category.dto.CategoryResult;
import com.jiho.anniehands.domain.product.ProductService;
import com.jiho.anniehands.domain.product.dto.ProductDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String list(
            @RequestParam(required = false) Integer categoryNo,
            @PageableDefault(size = 20, page = 0, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        CategoryResult categoryResult = categoryService.getCategoryInfo(categoryNo, pageable);
        log.info("카테고리 정보 ====> {}", categoryResult);
        prepareModel(pageable, model, categoryResult);
        return "page/product/list";
    }

    @GetMapping("/detail/{no}")
    public String detail(@PathVariable Long no, Model model) {
        ProductDetailDto productDetailDto = productService.findProductByNo(no);
        model.addAttribute("productDetail", productDetailDto);
        log.info("상품 디테일 정보 =======>>> {}", productDetailDto);
        return "page/product/detail";
    }

    private void prepareModel(Pageable pageable, Model model, CategoryResult categoryResult) {
        String sortString = pageable.getSort().toString().replace(": ", ",");
        int currentPageNo = pageable.getPageNumber();
        int totalPages = categoryResult.getProducts().getTotalPages();
        int startPage = Math.max(0, currentPageNo - 2); // 현재 페이지에서 2 빼기
        int endPage = Math.min(totalPages - 1, currentPageNo + 2); // 현재 페이지에서 2 더하기
        model.addAttribute("sortString", sortString);
        model.addAttribute("categoryResult", categoryResult);
        model.addAttribute("pageable", pageable);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);
    }
}
