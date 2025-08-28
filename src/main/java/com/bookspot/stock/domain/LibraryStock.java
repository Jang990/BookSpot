package com.bookspot.stock.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.global.DateHolder;
import com.bookspot.global.Events;
import com.bookspot.library.domain.Library;
import com.bookspot.stock.domain.event.LoanStateErrorEvent;
import com.bookspot.stock.domain.event.LoanStateRetryableErrorEvent;
import com.bookspot.stock.domain.event.LoanStateUpdatedEvent;
import com.bookspot.stock.domain.event.StockRefreshedEvent;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import com.bookspot.stock.domain.service.loanable.exception.ApiClientException;
import com.bookspot.stock.domain.service.loanable.exception.ServerException;
import com.bookspot.stock.domain.service.loanable.exception.TooManyRequestsException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    @Enumerated(EnumType.STRING)
    private LoanState loanState;

    public void refresh(DateHolder dateHolder) {
        if(isAlreadyRefreshed(dateHolder))
            return;
        Events.raise(new StockRefreshedEvent(this.id));
    }

    private boolean isAlreadyRefreshed(DateHolder dateHolder) {
        LocalDate now = dateHolder.now();
        return updatedAt.equals(now) || updatedAt.isAfter(now);
    }

    public void updateLoanState(LoanableResult result) {
        if (!result.hasBook()) {
            loanState = LoanState.ERROR;
            Events.raise(new LoanStateErrorEvent(library.getId(), book.getId()));
            return;
        }

        if (result.isLoanable())
            loanState = LoanState.LOANABLE;
        else
            loanState = LoanState.ON_LOAN;

        Events.raise(new LoanStateUpdatedEvent(library.getId(), book.getId()));
    }

    public void raiseErrorEvent(ApiClientException e) {
        if (e instanceof TooManyRequestsException || e instanceof ServerException) {
            Events.raise(new LoanStateRetryableErrorEvent(library.getId(), book.getId()));
            return;
        }

        Events.raise(new LoanStateErrorEvent(library.getId(), book.getId()));
    }
}
