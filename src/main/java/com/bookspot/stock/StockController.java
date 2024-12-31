package com.bookspot.stock;

import com.bookspot.library.LibraryDistanceDto;
import com.bookspot.library.LibraryService;
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
    private final LibraryService libraryService;

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

        List<LibraryDistanceDto> libraries = libraryService.findLibrariesWithin5km(
                stockSearchForm.getLatitude(), stockSearchForm.getLongitude());

        List<LibraryStockDto> result = List.of(
                new LibraryStockDto(
                        libraries.get(0).getLibraryName(), libraries.get(0).getDistance(),
                        10, 6,
                        List.of("AAA", "BBB", "CCC", "DDD")),
                new LibraryStockDto(
                        libraries.get(1).getLibraryName(), libraries.get(1).getDistance(),
                        10,8,
                        List.of("BBB", "CCC"))
        );
        model.addAttribute("contents", result);

        return "libraries/stock/search";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
