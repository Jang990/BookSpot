package com.bookspot.shelves.presentation;

import com.bookspot.global.auth.JwtProvider;
import com.bookspot.global.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShelvesManageController.class)
@Import(SecurityConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
class ShelvesManageControllerTest {
    private static final String COMMON_PATH = "/api/users/shelves/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    void 로그인하지_않은_사용자의_생성은_불가능() throws Exception {
        mockMvc.perform(post(COMMON_PATH))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 로그인한_사용자의_생성() throws Exception {
        mockMvc.perform(
                        post(COMMON_PATH)
                        .with(authentication(userAuth(10L)))
                )
                .andExpect(status().isUnauthorized());
    }

    private static UsernamePasswordAuthenticationToken userAuth(long userId) {
        return new UsernamePasswordAuthenticationToken(
                Long.toString(userId), null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}