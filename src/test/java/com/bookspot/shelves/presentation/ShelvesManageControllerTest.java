package com.bookspot.shelves.presentation;

import com.bookspot.ControllerTestNoSecurity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShelvesManageController.class)
@ControllerTestNoSecurity
class ShelvesManageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void 테스트() throws Exception {
        mockMvc.perform(post("/api/users/shelves/1"))
                .andExpect(status().isOk());
    }

}