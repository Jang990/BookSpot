package com.bookspot.users.presentation;

import com.bookspot.global.auth.JwtProvider;
import com.bookspot.global.auth.SecurityConfig;
import com.bookspot.users.application.UserBagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;



@WebMvcTest(UserBagController.class)
@Import(SecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@MockBean({JpaMetamodelMappingContext.class, JwtProvider.class})
class UserBagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBagService userBagService;

//    @Test
    void addBookToBag_인증된사용자() throws Exception {
        long bookId = 1L;
        String userIdStr = "123";

        // userBagService 호출 시 별도 반환 값 없음
        doNothing().when(userBagService).addBook(anyLong(), anyLong());

        mockMvc.perform(post("/api/users/bag/books/{bookId}", bookId)
                        .with(user(userIdStr))) // Spring Security MockUser 대신 principal로 설정
                .andExpect(status().isNoContent());

        verify(userBagService).addBook(123L, bookId);
    }
}