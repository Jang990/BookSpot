package com.bookspot.global.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

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
        return get(url, null, responseType);
    }

    public <T> T get(String url, Map<String, String> headers, Class<T> responseType) {
        log.trace("{} API 요청 시도", url);

        WebClient.RequestHeadersSpec<?> spec = client.get().uri(url);

        if (headers != null) {
            headers.forEach(spec::header);
        }
        return spec.retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        response -> Mono.error(new RequestException(response.statusCode())))
                .bodyToMono(responseType)
                .block();
    }
}
