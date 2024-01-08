package com.jiho.anniehands.product;

import com.jiho.anniehands.category.CategoryDto;
import com.jiho.anniehands.category.CategoryResult;
import com.jiho.anniehands.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        // 하위 카테고리를 조회했을 때 하위 카테고리 버튼을 유지하기 위해 상위 카테고리 번호로 하위 카테고리 조회
        if (categoryResult.getParent() != null) {
            List<CategoryDto> Children = categoryService.findSubcategories(categoryResult.getParent());
            categoryResult.setChildren(Children);
        }
        prepareModel(pageable, model, categoryResult);
        return "page/product/list";
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
