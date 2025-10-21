package com.bookspot.book.application;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.book.infra.BookSearchRepository;
import com.bookspot.book.presentation.response.BookPreviewListResponse;
import com.bookspot.book.presentation.response.BookPreviewResponse;
import com.bookspot.category.domain.BookCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock BookRepository repository;
    @Mock BookSearchRepository bookSearchRepository;
    @Mock BookCategoryRepository bookCategoryRepository;

    @InjectMocks
    BookService service;

    @Test
    void test() {

        Book book13 = mock(Book.class);
        when(book13.getId()).thenReturn(13L);
        when(book13.getSubjectCode()).thenReturn(null);
        when(book13.getCreatedAt()).thenReturn(LocalDateTime.now());

        Book book16 = mock(Book.class);
        when(book16.getId()).thenReturn(16L);
        when(book16.getSubjectCode()).thenReturn(null);
        when(book16.getCreatedAt()).thenReturn(LocalDateTime.now());

        Book book33 = mock(Book.class);
        when(book33.getId()).thenReturn(33L);
        when(book33.getSubjectCode()).thenReturn(null);
        when(book33.getCreatedAt()).thenReturn(LocalDateTime.now());

        Book book11 = mock(Book.class);
        when(book11.getId()).thenReturn(11L);
        when(book11.getSubjectCode()).thenReturn(null);
        when(book11.getCreatedAt()).thenReturn(LocalDateTime.now());

        when(repository.findAllById(any())).thenReturn(
                List.of(book13, book16, book33, book11)
        );

        BookPreviewListResponse result = service.findAll(List.of(13L, 16L, 33L, 11L));
        assertEquals(
                List.of(13L, 16L, 33L, 11L),
                result.books().stream().map(BookPreviewResponse::getId).toList()
        );

    }

}