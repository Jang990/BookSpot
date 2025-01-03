package com.bookspot.stock.presentation;

import com.bookspot.book.BookService;
import com.bookspot.library.LibraryDistanceDto;
import com.bookspot.library.LibraryService;
import com.bookspot.stock.application.LibraryStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final LibraryService libraryService;
    private final LibraryStockService libraryStockService;
    private final BookService bookService;

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
        model.addAttribute("stockSearchForm", stockSearchForm);
        if(bindingResult.hasErrors())
            return "libraries/stock/search";

        List<LibraryDistanceDto> libraries = libraryService.findLibrariesWithin5km(
                stockSearchForm.getLatitude(), stockSearchForm.getLongitude());

        List<LibraryStockDto> result = new LinkedList<>();
        for (LibraryDistanceDto library : libraries) {
            List<Long> unavailableBookIds = libraryStockService.findUnavailableBookIds(library.getLibraryId(), stockSearchForm.getBookId());
            result.add(
                    new LibraryStockDto(
                            library.getLibraryName(), library.getDistance(),
                            stockSearchForm.getBookId().size(), stockSearchForm.getBookId().size() - unavailableBookIds.size(),
                            bookService.findAll(unavailableBookIds)));
        }
        model.addAttribute("contents", result);

        return "libraries/stock/search";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
