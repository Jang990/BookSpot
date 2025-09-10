package com.bookspot.stock.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.global.DateHolder;
import com.bookspot.library.domain.Library;
import com.bookspot.stock.domain.exception.LibraryNotSupportsLoanStatusException;
import com.bookspot.stock.domain.exception.LibraryStockAlreadyRefreshedException;
import com.bookspot.stock.domain.exception.LibraryStockMismatchException;
import com.bookspot.stock.domain.service.loanable.LoanStateApiClient;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import com.bookspot.stock.domain.service.loanable.LoanableSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoanStateRefreshService {
    private final LoanStateApiClient loanStateApiClient;
    private final DateHolder dateHolder;

    public void checkRefreshAllowed(Library library, LibraryStock stock) {
        if (!library.isSupportsLoanStatus())
            throw new LibraryNotSupportsLoanStatusException(library.getId());
        if (stock.isAlreadyRefreshed(dateHolder))
            throw new LibraryStockAlreadyRefreshedException(stock.getId());
    }

    public LoanState refresh(
            LibraryStock stock,
            Book book,
            Library library
    ) {
        if(!stock.matches(book, library))
            throw new LibraryStockMismatchException(stock.getId(), book.getId(), library.getId());

        if(stock.isAlreadyRefreshed(dateHolder))
            return stock.getLoanState();

        LoanableResult result = loanStateApiClient.request(
                new LoanableSearchCond(
                        library.getLibraryCode(),
                        book.getIsbn13()
                )
        );

        stock.updateLoanState(result);
        return stock.getLoanState();
    }
}
