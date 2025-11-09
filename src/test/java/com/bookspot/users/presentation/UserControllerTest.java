package com.bookspot.users.presentation;

import com.bookspot.global.auth.JwtProvider;
import com.bookspot.global.auth.SecurityConfig;
import com.bookspot.users.application.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
@Import(SecurityConfig.class)
@MockBean({JpaMetamodelMappingContext.class, JwtProvider.class, UserService.class})
class UserControllerTest {
    @Autowired private MockMvc mockMvc;

    private final String FIND_MY_INFO_API = "/api/users/me";

    @Test
    void 내정보_조회는_인증되지_않은_사용자_접근불가() throws Exception {
        mockMvc.perform(get(FIND_MY_INFO_API))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 내정보_조회는_인증된_사용자만_가능() throws Exception {
        mockMvc.perform(
                apiWithAuth(get(FIND_MY_INFO_API), 1L))
                .andExpect(status().isOk());
    }

    public MockHttpServletRequestBuilder apiWithAuth(MockHttpServletRequestBuilder request, long userId) {
        return request.with(authentication(userAuth(userId)));
    }

    private static UsernamePasswordAuthenticationToken userAuth(long userId) {
        return new UsernamePasswordAuthenticationToken(
                Long.toString(userId), null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}