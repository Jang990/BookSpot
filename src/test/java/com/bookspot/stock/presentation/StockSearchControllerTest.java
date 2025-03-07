package com.bookspot.stock.presentation;

import com.bookspot.book.application.BookService;
import com.bookspot.library.LibraryService;
import com.bookspot.stock.application.LibraryStockService;
import com.bookspot.stock.application.query.StockQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("반경내의 도서관 책 재고 검색 테스트")
@WebMvcTest(StockSearchController.class)
class StockSearchControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean StockQueryService stockQueryService;
    
    @Test
    void 검색_필수요소() throws Exception {
        mvc.perform(get("/api/libraries/stocks")
                        .param("nwLat", "0")
                        .param("nwLon", "0")
                        .param("seLat", "0")
                        .param("seLon", "0")
                        .param("bookIds", "1,2,3")
                )
                .andExpect(status().isOk());
    }
}