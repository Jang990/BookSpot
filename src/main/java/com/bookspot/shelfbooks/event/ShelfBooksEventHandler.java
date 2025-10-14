package com.bookspot.shelfbooks.event;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.book.domain.exception.BookNotFoundException;
import com.bookspot.shelfbooks.domain.ShelfBook;
import com.bookspot.shelfbooks.domain.ShelfBookRepository;
import com.bookspot.shelfbooks.domain.exception.ShelfBookAlreadyExistsException;
import com.bookspot.shelfbooks.domain.exception.ShelfBookNotFoundException;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.domain.event.AddedBookToShelfEvent;
import com.bookspot.shelves.domain.event.AddedBookToShelvesEvent;
import com.bookspot.shelves.domain.event.RemovedBookToShelvesEvent;
import com.bookspot.shelves.domain.exception.ShelfNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ShelfBooksEventHandler {
    private final ShelvesRepository shelvesRepository;
    private final BookRepository bookRepository;
    private final ShelfBookRepository shelfBookRepository;

    @EventListener(AddedBookToShelfEvent.class)
    public void handle(AddedBookToShelfEvent event) {
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

    @EventListener(AddedBookToShelvesEvent.class)
    public void handle(AddedBookToShelvesEvent event) {
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

    @EventListener(RemovedBookToShelvesEvent.class)
    public void handle(RemovedBookToShelvesEvent event) {
        int targetShelfSize = event.shelfIds().size();

        int deletedShelfBookCount = shelfBookRepository.deleteByShelfIdsAndBookId(event.shelfIds(), event.bookId());
        if(deletedShelfBookCount < targetShelfSize)
            throw new ShelfBookNotFoundException(event.shelfIds(), event.bookId());

        int decreasedShelfCount = shelvesRepository.decreaseBookCountIn(event.shelfIds());
        if (decreasedShelfCount < targetShelfSize) {
            log.error("데이터 불일치 발생 == 삭제된 책장 도서 수: {}, 감소된 책장 수: {}, 이벤트: {}",
                    deletedShelfBookCount, decreasedShelfCount ,event);
            throw new ShelfNotFoundException(event.shelfIds());
        }
    }

    private List<Long> toShelfIds(List<Shelves> shelves) {
        return shelves.stream().map(Shelves::getId).toList();
    }

}
