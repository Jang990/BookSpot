package com.bookspot.book;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class BookService {
    public List<String> findAll(List<Long> bookIds) {
        List<String> result = new LinkedList<>();
        for (Long bookId : bookIds) {
            result.add(String.valueOf((char) ('A' + bookId - 1)).repeat(3));
        }
        return result;
    }
}
