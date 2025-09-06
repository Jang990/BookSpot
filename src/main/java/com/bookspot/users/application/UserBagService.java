package com.bookspot.users.application;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserBagService {
    private final UsersRepository usersRepository;
    private final BookRepository bookRepository;

    public void addBook(long userId, long bookId) {
        Users users = usersRepository.findByIdWithLock(userId)
                .orElseThrow(IllegalArgumentException::new);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(IllegalArgumentException::new);

        users.addBookToBag(book);
    }
}
