package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("책 상세정보 검색 테스트")
@WebMvcTest(BookController.class)
@MockBean(JpaMetamodelMappingContext.class)
class BookControllerTest_DetailSearch {

    @Autowired MockMvc mvc;
    @MockBean BookService bookService;

    @Test
    void 숫자_타입의_ID가_아닌_문자가_들어오면_안된다() throws Exception {
        mvc.perform(get("/api/books/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 정보를_반환해줌() throws Exception {
        mvc.perform(get("/api/books/1"))
                .andExpect(status().isOk());
    }
}