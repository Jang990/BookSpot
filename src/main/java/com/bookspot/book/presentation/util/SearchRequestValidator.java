package com.bookspot.book.presentation.util;

import com.bookspot.book.presentation.consts.BookBindingError;
import com.bookspot.book.presentation.consts.BookRequestCond;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@Service
public class SearchRequestValidator {
    public static void validatePageable(
            Pageable pageable, BindingResult bindingResult
    ) throws BindException {
        if (pageable.getPageNumber() > BookRequestCond.MAX_SEARCH_PAGE_NUMBER) {
            bindingResult.addError(BookBindingError.TOO_LARGE_PAGE_NUMBER.error());
            throw new BindException(bindingResult);
        }

        if (pageable.getPageSize() > BookRequestCond.MAX_SEARCH_PAGE_SIZE) {
            bindingResult.addError(BookBindingError.TOO_LARGE_PAGE_SIZE.error());
            throw new BindException(bindingResult);
        }
    }

}
