package com.bookspot.book.presentation.consts;

import com.bookspot.book.presentation.request.BookSearchAfterRequest;
import org.springframework.validation.ObjectError;

public enum BookBindingError {
    SEARCH_CRITERIA_MISSING(new ObjectError("", new String[]{"book.search.criteria.missing"}, null, null)),
    SEARCH_CRITERIA_INVALID(new ObjectError("", new String[]{"book.search.criteria.invalid"}, new Object[]{BookSearchAfterRequest.PARAM_LAST_LOAN_COUNT, BookSearchAfterRequest.PARAM_LAST_BOOK_ID}, null)),
    TOO_LARGE_PAGE_SIZE(new ObjectError("", new String[]{"book.search.page.size.tooLarge"}, new Object[]{BookRequestCond.MAX_SEARCH_PAGE_SIZE}, null)),
    TOO_LARGE_PAGE_NUMBER(new ObjectError("", new String[]{"book.search.page.number.tooLarge"}, new Object[]{BookRequestCond.MAX_SEARCH_PAGE_NUMBER}, null));

    private final ObjectError objectError;

    BookBindingError(ObjectError objectError) {
        this.objectError = objectError;
    }

    public ObjectError error() {
        return objectError;
    }
}
