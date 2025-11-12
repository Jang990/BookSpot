package com.bookspot.book.presentation;

import com.bookspot.ControllerTestNoSecurity;
import com.bookspot.book.application.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("책 발행연도 범위 검색 테스트")
@WebMvcTest(BookController.class)
@ControllerTestNoSecurity
class BookControllerTest_Year {
    @Autowired MockMvc mvc;
    @MockBean BookService bookService;

    @Test
    void 발행연도_검색_시_마이너스일_수_없음() throws Exception {
        mvc.perform(get("/api/books/by-ids?startYear=-1"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/books/by-ids?endYear=-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 발행연도_검색_시_시작연도가_종료연도보다_클_수_없음() throws Exception {
        mvc.perform(get("/api/books/by-ids?startYear=2222&endYear=1111"))
                .andExpect(status().isBadRequest());
    }

}