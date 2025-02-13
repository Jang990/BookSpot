package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("책 검색어 검색 테스트")
@WebMvcTest(BookController.class)
class BookControllerTest_TermSearch {
    private static final String TEST_API = "/api/books";

    @Autowired MockMvc mvc;
    @MockitoBean BookService bookService;

    @Test
    void 제목은_필수() throws Exception {
        mvc.perform(get(TEST_API))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 제목은_2글자_이하일_수_없다() throws Exception {
        mvc.perform(get(TEST_API.concat("?title=a")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 페이지_크기는_50을_초과할_수_없다() throws Exception {
        mvc.perform(get(TEST_API.concat("?size=51&title=abc")))
                .andExpect(status().isBadRequest());
    }

}