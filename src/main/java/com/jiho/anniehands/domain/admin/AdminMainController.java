package com.jiho.anniehands.domain.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminMainController {

    private final AdminCategoryService adminCategoryService;

    @GetMapping({"", "/"})
    public String admin() {
        return "page/admin/dashboard";
    }

}
