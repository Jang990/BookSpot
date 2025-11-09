package com.bookspot;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

public class WebSecurityAuthHelper {

    /**
     * 컨트롤러의 @AuthenticationPrincipal String userIdStr에 이 값이 들어감
     * @param request GET, POST, DELETE.. 등등 요청
     * @param userId  @AuthenticationPrincipal에 들어갈 userId
     * @return 체이닝해서 계속 사용 가능
     */
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
