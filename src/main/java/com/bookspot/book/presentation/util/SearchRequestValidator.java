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
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);

        int pageNumber = pageable.getPageNumber();
        if (pageNumber < BookRequestCond.MIN_PAGE_NUMBER
                || BookRequestCond.MAX_PAGE_NUMBER < pageNumber) {
            bindingResult.addError(BookBindingError.OUT_OF_PAGE_NUMBER.error());
            throw new BindException(bindingResult);
        }

        validatePageSize(pageable.getPageSize(), bindingResult);
    }

    public static void validatePageSize(int pageSize, BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);
        if (pageSize < BookRequestCond.MIN_PAGE_SIZE
                || BookRequestCond.MAX_PAGE_SIZE < pageSize) {
            bindingResult.addError(BookBindingError.OUT_OF_PAGE_SIZE.error());
            throw new BindException(bindingResult);
        }
    }

}
