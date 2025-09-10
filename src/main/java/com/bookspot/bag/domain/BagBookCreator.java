package com.bookspot.bag.domain;

import com.bookspot.bag.domain.exception.BookAlreadyInBagException;
import com.bookspot.book.domain.Book;
import com.bookspot.users.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BagBookCreator {
    private final BagBookRepository repository;

    public BagBook create(Users users, Book book) {
        if(repository.existsByUsersAndBook(users, book))
            throw new BookAlreadyInBagException(users.getId(), book.getId());
        return new BagBook(book, users);
    }
}
