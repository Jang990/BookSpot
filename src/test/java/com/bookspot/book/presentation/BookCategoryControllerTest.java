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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("카테고리 필터 포함 검색 테스트")
@WebMvcTest(BookCategoryController.class)
class BookCategoryControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    BookService bookService;

    @Test
    void categoryId는_0에서_999_사이의_숫자() throws Exception {
        mvc.perform(get("/api/categories/-1/books?lastLoanCount=111"))
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/categories/1000/books?lastBookId=111"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 페이지_크기는_1에서_50을_초과할_수_없다() throws Exception {
        mvc.perform(get("/api/categories/123/books?size=51&title=abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 페이지_번호는_0에서_49을_초과할_수_없다() throws Exception {
        mvc.perform(get("/api/categories/123/books?page=50&title=abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void search_After기반의_방식은_온전한_SearchAfterRequest_필드들이_필요() throws Exception {
        mvc.perform(get("/api/categories/123/books?lastLoanCount=111"))
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/categories/123/books?lastBookId=111"))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/categories/123/books?title=ABC",
            "/api/categories/5/books?bookIds=1,2,3",
            "/api/categories/62/books?bookIds=1&bookIds=2&bookIds=3",
            "/api/categories/115/books?title=ABC&bookIds=1,2,3",
            "/api/categories/456/books?title=ABC&bookIds=1,2,3&libraryId=1",
            "/api/categories/684/books?lastLoanCount=123&lastBookId=123"
    })
    void 정상처리_API(String testApi) throws Exception {
        mvc.perform(get(testApi))
                .andExpect(status().isOk());
    }
}