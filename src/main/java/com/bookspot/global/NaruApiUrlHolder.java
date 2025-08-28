package com.bookspot.global;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class NaruApiUrlHolder {
    @Value("${api.naru.url.loanable}")
    private String loanableApi;
}
