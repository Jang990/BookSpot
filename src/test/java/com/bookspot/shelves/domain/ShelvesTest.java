package com.bookspot.shelves.domain;

import com.bookspot.EventMockingHelper;
import com.bookspot.book.domain.Book;
import com.bookspot.shelves.domain.exception.ShelfBookFullException;
import com.bookspot.users.domain.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShelvesTest {
    @Mock Book book;
    @Mock Users user;
    Shelves shelf;

    @BeforeEach
    void beforeEach() {
        shelf = new Shelves(user, "임시 책장", false);
        ReflectionTestUtils.setField(shelf, "id", 1L); // 아이디 설정
    }

    @Test
    void 이미_가득찬_책장에_책_추가__예외처리() {
        ReflectionTestUtils.setField(shelf, "bookCount", ShelfConst.MAX_SHELF_BOOKS_SIZE);
        assertThrows(ShelfBookFullException.class, () -> shelf.addBook(book));
    }

    @Test
    void 책장에_책_추가_성공__책_카운트_증가() {
        EventMockingHelper.runWithoutEvents(() -> {
            shelf.addBook(book);
        });
        assertEquals(shelf.getBookCount(), 1);
    }


}