package com.bookspot.book.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("책 랭킹 검색")
@WebMvcTest(BookRankingController.class)
class BookRankingControllerTest {

    @Autowired
    MockMvc mvc;

    private static final String baseUrl = "/api/books/rankings?";

    @Test
    void 랭킹과_관련된_모든_조건이_들어온다면_정상처리() throws Exception {
        mvc.perform(get(baseUrl.concat("gender=MALE&age=AGE_20_29&period=MONTHLY")))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            baseUrl,
            baseUrl + "gender=MALE&age=AGE_12_12",
            baseUrl + "age=AGE_20_29"
    })
    void 기간_조건만_있다면_다른_조건은_디폴트값으로_처리(String url) throws Exception {
        mvc.perform(get(url)).andExpect(status().isBadRequest());
    }

    @Test
    void 기간_조건이_누락되면_badRequest() throws Exception {
        mvc.perform(get(baseUrl.concat("age=AGE_20_29")))
                .andExpect(status().isBadRequest());
    }


    @ParameterizedTest
    @ValueSource(strings = {
            baseUrl + "gender=FREE&age=AGE_20_29&period=MONTHLY",
            baseUrl + "gender=MALE&age=AGE_12_123&period=MONTHLY",
            baseUrl + "gender=MALE&age=AGE_20_29&period=YEAR"
    })
    void 조건을_request의_enum으로_변환할_수_없다면_badRequest(String url) throws Exception {
        mvc.perform(get(url)).andExpect(status().isBadRequest());
    }

}