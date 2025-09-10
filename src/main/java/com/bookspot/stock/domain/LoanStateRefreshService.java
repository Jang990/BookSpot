package com.bookspot.stock.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.global.DateHolder;
import com.bookspot.library.domain.Library;
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
        if(!library.isSupportsLoanStatus())
            throw new IllegalArgumentException("도서관 대출 현황을 지원하지 않는 도서관입니다."); // 422
        if(stock.isAlreadyRefreshed(dateHolder))
            throw new IllegalArgumentException("이미 Refresh된 대출 현황입니다"); // 429
    }

    public LoanState refresh(
            LibraryStock stock,
            Book book,
            Library library
    ) {
        if(!stock.matches(book, library))
            throw new IllegalArgumentException("요청된 book/library가 stock과 일치하지 않습니다");

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
