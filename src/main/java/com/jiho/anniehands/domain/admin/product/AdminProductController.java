package com.jiho.anniehands.domain.admin.product;

import com.jiho.anniehands.common.file.FileService;
import com.jiho.anniehands.domain.admin.AdminCategoryService;
import com.jiho.anniehands.domain.category.dto.CategoryDto;
import com.jiho.anniehands.domain.category.dto.CategoryResultAdmin;
import com.jiho.anniehands.domain.options.OptionsDto;
import com.jiho.anniehands.domain.options.OptionsService;
import com.jiho.anniehands.domain.product.dto.ProductCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product")
@Slf4j
public class AdminProductController {

    private final AdminCategoryService adminCategoryService;
    private final OptionsService optionsService;
    private final AdminProductService adminProductService;
    private final FileService fileService;

    @GetMapping("/list")
    public String getProductList(@PageableDefault(size = 20, page = 0, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(required = false) Integer categoryNo,
                                 @RequestParam(required = false) String opt,
                                 @RequestParam(required = false) String keyword,
                                 Model model) {

        CategoryResultAdmin categoryResult;
        if (StringUtils.hasText(opt) && StringUtils.hasText(keyword)) {
            // 검색 조건(opt와 keyword)이 있는 경우, 검색 메소드를 호출
            categoryResult = adminCategoryService.searchProducts(opt, keyword, pageable);
        } else {
            // 검색 조건이 없는 경우, 기본 카테고리 정보를 가져오는 메소드를 호출
            categoryResult = adminCategoryService.getCategoryInfoForAdmin(categoryNo, pageable);
        }

        preparePageModel(pageable, model, categoryResult);
        model.addAttribute("opt", opt);
        model.addAttribute("keyword", keyword);
        return "page/admin/list";
    }

    @GetMapping("/create")
    public String productCreateForm(Model model) {
        prepareOptionsModel(model);
        ProductCreateForm productCreateForm = new ProductCreateForm();
        model.addAttribute("productCreateForm", productCreateForm);
        return "page/admin/create";
    }

    @PostMapping("/create")
    public String productCreate(@Valid ProductCreateForm productCreateForm, BindingResult error, Model model) {
        log.info("productCreateForm ====> {}", productCreateForm);
        if (error.hasErrors()) {
            prepareOptionsModel(model);
            return "page/admin/create";
        }
        adminProductService.createProduct(productCreateForm);
        return "redirect:/admin/product/list";
    }

    @GetMapping("/delete/{no}")
    public String productDetele(@PathVariable Long no) {
        adminProductService.productDelete(no);
        return "redirect:/admin/product/list";
    }

    @GetMapping("/children-categories")
    @ResponseBody
    public List<CategoryDto> getChildrenCategory(Integer parentCategoryNo) {
        return adminCategoryService.getChildrenCategories(parentCategoryNo);
    }

    private void prepareOptionsModel(Model model) {
        List<CategoryDto> parentCategories = adminCategoryService.getParentCategories();
        OptionsDto optionsDto = optionsService.findAll();
        model.addAttribute("parentCategories", parentCategories);
        model.addAttribute("options", optionsDto);
    }

    private void preparePageModel(Pageable pageable, Model model, CategoryResultAdmin categoryResult) {
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
