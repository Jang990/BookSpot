package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("책 검색어 검색 테스트")
@WebMvcTest(BookController.class)
class BookControllerTest_TermSearch {
    @Autowired MockMvc mvc;
    @MockBean BookService bookService;

    @Test
    void 검색어와_책ID_둘_다_없으면_안된다() throws Exception {
        mvc.perform(get("/api/books"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 검색어는_2글자_이하일_수_없다() throws Exception {
        mvc.perform(get("/api/books?title=a"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 페이지_크기는_50을_초과할_수_없다() throws Exception {
        mvc.perform(get("/api/books?size=51&title=abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 페이지_번호는_50을_초과할_수_없다() throws Exception {
        mvc.perform(get("/api/books?page=51&title=abc"))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/books?title=ABC",
            "/api/books?bookIds=1,2,3",
            "/api/books?bookIds=1&bookIds=2&bookIds=3",
            "/api/books?title=ABC&bookIds=1,2,3",
            "/api/books?title=ABC&bookIds=1,2,3&libraryId=1"
    })
    void 제목_책ID_도서관ID_정상처리(String testApi) throws Exception {
        mvc.perform(get(testApi))
                .andExpect(status().isOk());
    }

}