package com.jiho.anniehands.domain.admin;

import com.jiho.anniehands.domain.category.dto.CategoryDto;
import com.jiho.anniehands.domain.category.dto.CategoryResultAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminCategoryService adminCategoryService;

    @GetMapping({"", "/"})
    public String admin() {
        return "page/admin/dashboard";
    }

    @GetMapping("/product/list")
    public String productList(@RequestParam(required = false) Integer categoryNo,
                              @PageableDefault(size = 20, page = 0, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {
        CategoryResultAdmin categoryResult = adminCategoryService.getCategoryInfoForAdmin(categoryNo, pageable);
        prepareModel(pageable, model, categoryResult);
        return "page/admin/list";
    }
    @GetMapping("/product/search")
    public String productSearch(@PageableDefault(size = 20, page = 0, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                @RequestParam(required = false) Integer categoryNo,
                                @RequestParam(required = false) String opt,
                                @RequestParam(required = false) String keyword,
                                Model model) {
        CategoryResultAdmin categoryResult = adminCategoryService.searchProducts(categoryNo, opt, keyword, pageable);
        prepareModel(pageable, model, categoryResult);
        return "page/admin/list";
    }

    @GetMapping("/product/create")
    public String productCreateForm(Model model) {
        List<CategoryDto> parentCategories = adminCategoryService.fineGetParentCategories();
        model.addAttribute("parentCategories", parentCategories);
        return "page/admin/create";
    }

    @PostMapping("/product/create")
    public String productCreate() {
        return "redirect:/page/admin/list";
    }

    private void prepareModel(Pageable pageable, Model model, CategoryResultAdmin categoryResult) {
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
