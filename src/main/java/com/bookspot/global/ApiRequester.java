package com.bookspot.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class ApiRequester {
    private final WebClient client;

    public ApiRequester() {
        client = WebClient.builder()
                .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();
    }

    public <T> T get(String url, Class<T> responseType) {
        log.trace("{} API 요청 시도", url);
        return client.get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
}
