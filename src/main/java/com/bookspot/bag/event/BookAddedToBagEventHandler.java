package com.bookspot.bag.event;

import com.bookspot.bag.domain.BagBook;
import com.bookspot.bag.domain.BagBookCreator;
import com.bookspot.bag.domain.BagBookRepository;
import com.bookspot.bag.domain.exception.BookAlreadyRemovedFromBagException;
import com.bookspot.bag.domain.exception.BookBagAlreadyEmptyException;
import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import com.bookspot.users.domain.event.BookAddedToBagEvent;
import com.bookspot.users.domain.event.BookBagClearedEvent;
import com.bookspot.users.domain.event.BookDeletedFromBagEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookAddedToBagEventHandler {
    private final BookRepository bookRepository;
    private final UsersRepository usersRepository;
    private final BagBookRepository bagBookRepository;
    private final BagBookCreator bagBookCreator;

    @EventListener(BookAddedToBagEvent.class)
    public void handle(BookAddedToBagEvent event) {
        Book book = bookRepository.findById(event.bookId())
                .orElseThrow(IllegalArgumentException::new);
        Users users = usersRepository.findById(event.userId())
                .orElseThrow(IllegalArgumentException::new);

        BagBook bagBook = bagBookCreator.create(users, book);
        bagBookRepository.save(bagBook);
    }

    @EventListener(BookDeletedFromBagEvent.class)
    public void handle(BookDeletedFromBagEvent event) {
        int result = bagBookRepository.deleteByUsersIdAndBookId(event.userId(), event.bookId());
        if(result == 0)
            throw new BookAlreadyRemovedFromBagException(event.userId(), event.bookId());
    }

    @EventListener(BookBagClearedEvent.class)
    public void handle(BookBagClearedEvent event) {
        int result = bagBookRepository.deleteByUsersId(event.userId());
        if(result == 0)
            throw new BookBagAlreadyEmptyException(event.userId());
    }
}
