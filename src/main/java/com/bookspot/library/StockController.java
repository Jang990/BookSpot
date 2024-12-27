package com.bookspot.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StockController {
    @GetMapping("/libraries/stock/search-setting")
    public String settingPage() {
        return "libraries/stock/search-setting";
    }

    @GetMapping("/libraries/stock")
    public String searchLibrariesStock(StockSearchForm stockSearchForm) {
        return "libraries/stock/search";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
