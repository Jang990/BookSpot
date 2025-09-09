package com.bookspot.global;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DateHolder {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
    public Date nowDate() {
        return new Date();
    }
}
