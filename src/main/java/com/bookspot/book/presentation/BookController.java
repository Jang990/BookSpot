package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import com.bookspot.stock.presentation.StockSearchForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/books")
    @ResponseBody
    public ResponseEntity<Slice<BookSummaryResponse>> findBook(
            @Valid BookSearchRequest request,
            Pageable pageable) {
        if(pageable.getPageSize() > 50)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookService.findBook(request.getTitle(), pageable));
    }

    @GetMapping("/libraries/stock/book")
    public String searchBookPage(
            Model model,
            @Valid StockSearchForm stockSearchForm,
            BindingResult bindingResult) {
        model.addAttribute("stockSearchForm", stockSearchForm);
        if(bindingResult.hasErrors())
            return "book/search";

        List<BookDto> selectedBooks = bookService.findAll(stockSearchForm.getBookId());
        model.addAttribute("selectedBooks", selectedBooks);

        return "book/search";
    }
}
