package com.bookspot.users.application;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import com.bookspot.users.domain.exception.UserNotFoundException;
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
                .orElseThrow(() -> new UserNotFoundException(userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(IllegalArgumentException::new);

        users.addBookToBag(book);
    }

    public void deleteBook(long userId, long bookId) {
        Users users = usersRepository.findByIdWithLock(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(IllegalArgumentException::new);

        users.deleteBookFromBag(book);
    }

    public void clearBag(long userId) {
        Users users = usersRepository.findByIdWithLock(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        users.clearBag();
    }
}
