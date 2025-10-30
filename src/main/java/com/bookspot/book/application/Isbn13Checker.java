package com.bookspot.book.application;

import org.springframework.stereotype.Service;

@Service
public class Isbn13Checker {
    public boolean isIsbn13(String keyword) {
        return keyword.length() == 13 && hasOnlyNumber(keyword);
    }

    private boolean hasOnlyNumber(String keyword) {
        for (int i = 0; i < keyword.length(); i++) {
            int digital = keyword.charAt(i) - '0';
            if(digital < 0 || 9 < digital)
                return false;
        }
        return true;
    }
}
