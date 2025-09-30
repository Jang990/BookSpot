package com.bookspot.shelves.presentation;

import com.bookspot.WebSecurityAuthHelper;
import com.bookspot.global.auth.JwtProvider;
import com.bookspot.global.auth.SecurityConfig;
import com.bookspot.shelves.application.ShelvesManageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.bookspot.WebSecurityAuthHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShelvesManageController.class)
@Import(SecurityConfig.class)
@MockBean({JpaMetamodelMappingContext.class, JwtProvider.class, ShelvesManageService.class})
class ShelvesManageControllerTest_Delete {
    private static final String COMMON_PATH = "/api/users/shelves/12";

    @Autowired private MockMvc mockMvc;

    @Test
    void 로그인하지_않은_사용자는_삭제_불가능() throws Exception {
        mockMvc.perform(delete(COMMON_PATH))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 책장_삭제() throws Exception {
        mockMvc.perform(delete(COMMON_PATH)
                        .with(authentication(userAuth(1L))))
                .andExpect(status().isNoContent());
    }

}