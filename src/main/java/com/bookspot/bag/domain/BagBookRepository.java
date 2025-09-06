package com.bookspot.bag.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BagBookRepository extends JpaRepository<BagBook, Long> {
    boolean existsByUsersAndBook(Users users, Book book);
}
