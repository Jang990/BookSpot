package com.bookspot;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

public class WebSecurityAuthHelper {
    public static UsernamePasswordAuthenticationToken userAuth(long userId) {
        return new UsernamePasswordAuthenticationToken(
                Long.toString(userId), null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
