package com.bookspot.stock.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.global.DateHolder;
import com.bookspot.library.domain.Library;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class LibraryStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private LoanState loanState;

    public void refresh(DateHolder dateHolder) {
        if(isAlreadyRefreshed(dateHolder))
            return;
//        Events.raise(new StockRefreshedEvent(this.id));
    }

    public boolean isAlreadyRefreshed(DateHolder dateHolder) {
        LocalDateTime now = dateHolder.now();
        return updatedAt.equals(now) || updatedAt.isAfter(now);
    }

    public void updateLoanState(LoanableResult result) {
        if (!result.hasBook()) {
            loanState = LoanState.ERROR;
//            Events.raise(new LoanStateErrorEvent(library.getId(), book.getId()));
            return;
        }

        if (result.isLoanable())
            loanState = LoanState.LOANABLE;
        else
            loanState = LoanState.ON_LOAN;

//        Events.raise(new LoanStateUpdatedEvent(library.getId(), book.getId()));
    }

    /*public void raiseErrorEvent(LoanStateApiException e) {
        if (e.isRetryable()) {
            Events.raise(new LoanStateRetryableErrorEvent(library.getId(), book.getId()));
            return;
        }

        Events.raise(new LoanStateErrorEvent(library.getId(), book.getId()));
    }*/

    public boolean matches(Book book, Library library) {
        return book.getId().equals(getBook().getId())
                && library.getId().equals(getLibrary().getId());
    }
}
