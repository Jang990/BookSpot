package com.bookspot.global;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateHolder {
    public LocalDate now() {
        return LocalDate.now();
    }
}
