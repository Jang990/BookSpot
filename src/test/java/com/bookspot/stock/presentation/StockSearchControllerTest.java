package com.bookspot.stock.presentation;

import com.bookspot.stock.application.query.StockQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("반경내의 도서관 책 재고 검색 테스트")
@WebMvcTest(StockSearchController.class)
class StockSearchControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean StockQueryService stockQueryService;
    
    @Test
    void 검색_필수_요소() throws Exception {
        mvc.perform(get("/api/libraries/stocks")
                        .param("libraryIds", "1")
                        .param("bookIds", "1,2,3")
                )
                .andExpect(status().isOk());
    }
}