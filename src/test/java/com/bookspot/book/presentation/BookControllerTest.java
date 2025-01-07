package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    BookService bookService;


    @Test
    void 제목은_2글자_이하일_수_없다() throws Exception {
        mvc.perform(get("/api/book?title=a"))
                .andExpect(status().isBadRequest());
    }

}