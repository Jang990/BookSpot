package com.bookspot.book.presentation;

import com.bookspot.ControllerTestNoSecurity;
import com.bookspot.book.application.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("책 Ids 검색 테스트")
@WebMvcTest(BookController.class)
@ControllerTestNoSecurity
class BookControllerTest_Ids {
    @Autowired MockMvc mvc;
    @MockBean BookService bookService;

    @Test
    void 검색_시_ids는_필수() throws Exception {
        mvc.perform(get("/api/books/by-ids"))
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/books/by-ids?abc=aaa"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ids가_있어야만_정상처리() throws Exception {
        mvc.perform(get("/api/books/by-ids?bookIds=123"))
                .andExpect(status().isOk());
    }

    @Test
    void ids가_50개보다_많으면_안됨() throws Exception {
        mvc.perform(get("/api/books/by-ids?bookIds=" + "123,".repeat(51)))
                .andExpect(status().isBadRequest());
    }

}