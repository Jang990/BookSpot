package com.bookspot.users.presentation;

import com.bookspot.SpringBootWithH2Test;
import com.bookspot.users.domain.OAuthProvider;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootWithH2Test
@Transactional
class UserControllerApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersRepository usersRepository;

    private Users testUser;

    @BeforeEach
    void setUp() throws InterruptedException {
        // 유저 생성
        testUser = Users.createUser("123456789", OAuthProvider.GOOGLE, "testUser@example.com");
        usersRepository.save(testUser);
    }

    @Test
    void 내정보_조회_API() throws Exception {
        mockMvc.perform(
                apiWithAuth(get("/api/users/me"), testUser.getId())
        )
                .andDo(print())
                .andExpect(jsonPath("$.email").value(testUser.getEmail()))
                .andExpect(jsonPath("$.provider").value(testUser.getProvider().toString()))
                .andExpect(jsonPath("$.createdAt").value(testUser.getCreatedAt().toString()));
    }

    public static MockHttpServletRequestBuilder apiWithAuth(MockHttpServletRequestBuilder request, long userId) {
        return request.with(authentication(userAuth(userId)));
    }

    private static UsernamePasswordAuthenticationToken userAuth(long userId) {
        return new UsernamePasswordAuthenticationToken(
                Long.toString(userId), null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}