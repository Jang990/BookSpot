package com.bookspot.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class StockController {
    @GetMapping("/libraries/stock/search-setting")
    public String settingPage() {
        return "libraries/stock/search-setting";
    }

    @PostMapping("/libraries/stock")
    public String searchLibrariesStock(StockSearchForm stockSearchForm) {
        return "redirect:/";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
