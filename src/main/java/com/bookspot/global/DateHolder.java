package com.bookspot.global;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateHolder {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
