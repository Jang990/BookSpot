package com.bookspot.shelves.presentation;

import com.bookspot.global.auth.JwtProvider;
import com.bookspot.global.auth.SecurityConfig;
import com.bookspot.shelves.application.ShelvesManageService;
import com.bookspot.shelves.presentation.dto.request.ShelfCreationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShelvesManageController.class)
@Import(SecurityConfig.class)
@MockBean({JpaMetamodelMappingContext.class, JwtProvider.class, ShelvesManageService.class})
class ShelvesManageControllerTest_Creation {
    private static final String COMMON_PATH = "/api/users/shelves";

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper om;

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
                                .content(creationRequest("개발 필독서", true))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("잘못된_생성_요청")
    void 생성_400오류_케이스(ShelfCreationRequest request) throws Exception {
        mockMvc.perform(
                        post(COMMON_PATH)
                                .with(authentication(userAuth(10L)))
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    static Stream<Arguments> 잘못된_생성_요청() {
        return Stream.of(
                Arguments.of(new ShelfCreationRequest("", true)),
                Arguments.of(new ShelfCreationRequest(" ".repeat(5), true)),
                Arguments.of(new ShelfCreationRequest(null, true)),
                Arguments.of(new ShelfCreationRequest("A".repeat(101), true)),
                Arguments.of(new ShelfCreationRequest("고전문학", null))
        );
    }

    private String creationRequest(String name, Boolean isPublic) throws JsonProcessingException {
        return om.writeValueAsString(new ShelfCreationRequest(name, isPublic));
    }

    private static UsernamePasswordAuthenticationToken userAuth(long userId) {
        return new UsernamePasswordAuthenticationToken(
                Long.toString(userId), null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}