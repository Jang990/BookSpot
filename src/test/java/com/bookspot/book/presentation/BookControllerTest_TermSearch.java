package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("책 검색어 검색 테스트")
@WebMvcTest(BookController.class)
@MockBean(JpaMetamodelMappingContext.class)
class BookControllerTest_TermSearch {
    @Autowired MockMvc mvc;
    @MockBean BookService bookService;

    @Test
    void categoryId는_0에서_999_사이의_숫자() throws Exception {
        mvc.perform(get("/api/books?categoryId=-1"))
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/books?categoryId=1000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void libraryId는_1이상의_숫자() throws Exception {
        mvc.perform(get("/api/books?libraryId=0"))
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/books?libraryId=-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 페이지_크기는_1에서_50을_초과할_수_없다() throws Exception {
        mvc.perform(get("/api/books?size=51&title=abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 페이지_번호는_0에서_49을_초과할_수_없다() throws Exception {
        mvc.perform(get("/api/books?page=50&title=abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void search_After기반의_방식은_온전한_SearchAfterRequest_필드들이_필요() throws Exception {
        mvc.perform(get("/api/books?lastLoanCount=111"))
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/books?lastBookId=111"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void search_After기반의_방식에서_score_기반_정렬이_필요하다면_검색어는_필수() throws Exception {
        mvc.perform(get("/api/books?lastLoanCount=111&lastBookId=111&lastScore=123.4567"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void search_After기반의_방식에서_lastScore에_문자가_포함돼_있으면_안된다() throws Exception {
        mvc.perform(get("/api/books?lastLoanCount=111&lastBookId=111&lastScore=abc"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/books?lastLoanCount=111&lastBookId=111&lastScore=12.345ac56"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 카테고리_레벨이_부정확하면_오류() throws Exception {
        mvc.perform(get("/api/books?categoryId=123&categoryLevel=ABC"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 카테고리_레벨_or_카테고리_아이디_둘_중_하나만_있으면_오류() throws Exception {
        mvc.perform(get("/api/books?categoryId=123"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/books?categoryLevel=TOP"))
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/books?lastLoanCount=123&lastBookId=123&categoryId=123"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/books?lastLoanCount=123&lastBookId=123&categoryLevel=TOP"))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/books?title=ABC",
            "/api/books?bookIds=1,2,3",
            "/api/books?bookIds=1&bookIds=2&bookIds=3",
            "/api/books?title=ABC&bookIds=1,2,3",
            "/api/books?title=ABC&bookIds=1,2,3&libraryId=1",
            "/api/books?lastLoanCount=123&lastBookId=123",
            "/api/books?libraryId=123456",
            "/api/books?lastLoanCount=111&lastBookId=111&lastScore=123.4567&title=한강",

            "/api/books?categoryId=123&categoryLevel=TOP",
            "/api/books?categoryId=123&categoryLevel=MID",
            "/api/books?categoryId=123&categoryLevel=LEAF",
            "/api/books?lastLoanCount=123&lastBookId=123&categoryId=123&categoryLevel=TOP",
    })
    void 정상처리_API(String testApi) throws Exception {
        mvc.perform(get(testApi))
                .andExpect(status().isOk());
    }

}