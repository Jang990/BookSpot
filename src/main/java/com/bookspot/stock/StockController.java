package com.bookspot.stock;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StockController {
    @GetMapping("/libraries/stock/search-setting")
    public String settingPage(Model model) {
        model.addAttribute("stockSearchForm", new StockSearchForm());
        return "libraries/stock/search-setting";
    }

    @GetMapping("/libraries/stock")
    public String searchLibrariesStock(
            Model model,
            @Valid StockSearchForm stockSearchForm,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "libraries/stock/search-setting";

        model.addAttribute("contents", List.of(
                new LibraryStockDto("A도서관", 3.41, 10, 6, List.of(
                        "AAA", "BBB", "CCC", "DDD"
                )),
                new LibraryStockDto("B도서관", 4.41, 10, 8, List.of(
                        "BBB", "CCC"
                ))
        ));

        return "libraries/stock/search";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
