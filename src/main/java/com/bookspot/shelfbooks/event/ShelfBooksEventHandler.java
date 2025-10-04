package com.bookspot.shelfbooks.event;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.book.domain.exception.BookNotFoundException;
import com.bookspot.shelfbooks.domain.ShelfBook;
import com.bookspot.shelfbooks.domain.ShelfBookRepository;
import com.bookspot.shelfbooks.domain.exception.ShelfBookAlreadyExistsException;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.domain.event.AddedBookToShelf;
import com.bookspot.shelves.domain.event.AddedBookToShelves;
import com.bookspot.shelves.domain.exception.ShelfNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShelfBooksEventHandler {
    private final ShelvesRepository shelvesRepository;
    private final BookRepository bookRepository;
    private final ShelfBookRepository shelfBookRepository;

    @EventListener(AddedBookToShelf.class)
    public void handle(AddedBookToShelf event) {
        Shelves shelves = shelvesRepository.findById(event.shelfId())
                .orElseThrow(ShelfNotFoundException::new);
        Book book = bookRepository.findById(event.bookId())
                .orElseThrow(BookNotFoundException::new);

        if(shelfBookRepository.existsByShelfAndBook(shelves, book))
            throw new ShelfBookAlreadyExistsException(shelves.getId(), book.getId());

        // TODO: 이후에 책장 책의 idx 변경 기능 추가하기
        ShelfBook shelfBook = new ShelfBook(shelves, book, 0);
        shelfBookRepository.save(shelfBook);
    }

    @EventListener(AddedBookToShelves.class)
    public void handle(AddedBookToShelves event) {
        List<Shelves> shelves = shelvesRepository.findAllById(event.shelfIds());
        Book book = bookRepository.findById(event.bookId())
                .orElseThrow(BookNotFoundException::new);
        if (shelves.size() != event.shelfIds().size())
            throw new ShelfNotFoundException(toShelfIds(shelves));

        if (shelfBookRepository.existsByShelfIdInAndBookId(toShelfIds(shelves), event.bookId()))
            throw new ShelfBookAlreadyExistsException(toShelfIds(shelves), event.bookId());

        List<ShelfBook> shelfBooks = new ArrayList<>();
        for (Shelves shelf : shelves) {
            // TODO: 인덱스 관리 필요
            shelfBooks.add(new ShelfBook(shelf, book, 0));
        }

        shelvesRepository.increaseBookCountIn(toShelfIds(shelves));
        shelfBookRepository.saveAll(shelfBooks);
    }

    private List<Long> toShelfIds(List<Shelves> shelves) {
        return shelves.stream().map(Shelves::getId).toList();
    }

}
