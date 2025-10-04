package com.bookspot.shelves.application;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.book.domain.exception.BookNotFoundException;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesManager;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.domain.exception.ShelfForbiddenException;
import com.bookspot.shelves.domain.exception.ShelfNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShelfBooksManageService {
    private final BookRepository bookRepository;
    private final ShelvesRepository shelvesRepository;
    private final ShelvesManager shelvesManager;

    public void addBookToShelves(long loginUserId, long shelfId, long bookId) {
        Shelves shelf = shelvesRepository.findById(shelfId)
                .orElseThrow(ShelfNotFoundException::new);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        if(shelf.isOwnerBy(loginUserId))
            shelf.addBook(book);
        else
            throw new ShelfForbiddenException(loginUserId, shelfId);
    }

    public void addBookToShelves(long loginUserId, List<Long> shelfIds, long bookId) {
        List<Shelves> shelves = shelvesRepository.findByIdsWithLock(loginUserId, shelfIds);

        if(shelves.size() != shelfIds.size())
            throw new ShelfNotFoundException(shelfIds);

        shelvesManager.addBookToShelves(shelves, loginUserId, bookId);
    }
}
