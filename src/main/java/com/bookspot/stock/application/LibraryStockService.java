package com.bookspot.stock.application;

import com.bookspot.stock.domain.LibraryStock;
import com.bookspot.stock.domain.LibraryStockRepository;
import com.bookspot.stock.domain.LoanState;
import com.bookspot.stock.presentation.response.LoanStateResponseEnum;
import com.bookspot.stock.presentation.response.StockLoanStateResponse;
import com.bookspot.stock.presentation.response.StockLoanStateResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryStockService {
    private final LibraryStockRepository repository;

    public List<Long> findAvailableBookIds(long libraryId, List<Long> bookIds) {
        List<Long> result = new LinkedList<>();

        for (Long bookId : bookIds) {
            if (repository.existsByLibraryIdAndBookId(libraryId, bookId))
                result.add(bookId);
        }

        return result;
    }

    public StockLoanStateResponseList findCurrentLoanState(List<Long> stockIds) {
        List<LibraryStock> stocks = repository.findAllById(stockIds);
        if(stocks.size() != stockIds.size())
            throw new IllegalArgumentException("확인할 수 없는 stockId");

        return new StockLoanStateResponseList(
                stocks.stream()
                        .map(LibraryStockService::transform)
                        .toList()
        );
    }

    public static StockLoanStateResponse transform(LibraryStock stock) {
        return new StockLoanStateResponse(
                stock.getId(),
                stock.getUpdatedAt(),
                transform(stock.getLoanState())
        );
    }

    public static LoanStateResponseEnum transform(LoanState loanState) {
        return switch (loanState) {
            case LOANABLE -> LoanStateResponseEnum.LOANABLE;
            case ON_LOAN -> LoanStateResponseEnum.ON_LOAN;
            case UNKNOWN -> LoanStateResponseEnum.UNKNOWN;
            case ERROR -> LoanStateResponseEnum.ERROR;
        };
    }
}
