package com.bookspot.bag.domain;

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
            throw new IllegalArgumentException("이미 책가방에 있는 책을 생성할 수 없습니다.");
        return new BagBook(book, users);
    }
}
