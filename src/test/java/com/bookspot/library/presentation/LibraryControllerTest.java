package com.bookspot.library.presentation;

import com.bookspot.global.SecurityConfig;
import com.bookspot.library.application.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
class LibraryControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean LibraryService libraryService;

    @Test
    void 도서관_위도_경도_검색() throws Exception {
        mvc.perform(get("http://localhost:8080/api/libraries?nwLat=0&nwLon=0&seLat=0&seLon=0"))
                .andExpect(status().isOk());
    }

    @Test
    void 도서관_단일_검색() throws Exception {
        mvc.perform(get("http://localhost:8080/api/libraries/1"))
                .andExpect(status().isOk());
    }
}