package com.bookspot.bag.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BagBookRepository extends JpaRepository<BagBook, Long> {

    List<BagBook> findByUsersId(long userId);

    boolean existsByUsersAndBook(Users users, Book book);

    @Modifying
    @Query("delete from BagBook b where b.users.id = :userId and b.book.id = :bookId")
    int deleteByUsersIdAndBookId(
            @Param("userId") long userId,
            @Param("bookId") long bookId
    );

}
