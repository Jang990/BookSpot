package com.bookspot.global.auth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OAuthClientProperties {
    @Value("${google.client.id}")
    private String googleClientId;
}
