package com.bookspot.book.presentation.consts;

import com.bookspot.book.presentation.request.BookSearchAfterRequest;
import org.springframework.validation.ObjectError;

public enum BookBindingError {
//    SEARCH_CRITERIA_MISSING(new ObjectError("", new String[]{"book.search.criteria.missing"}, null, null)),
    SEARCH_CRITERIA_INVALID(new ObjectError("", new String[]{"book.search.criteria.invalid"}, new Object[]{BookSearchAfterRequest.PARAM_LAST_LOAN_COUNT, BookSearchAfterRequest.PARAM_LAST_BOOK_ID}, null)),
    OUT_OF_PAGE_SIZE(new ObjectError("", new String[]{"book.search.page.size.outOfRange"}, new Object[]{BookRequestCond.MIN_PAGE_SIZE,BookRequestCond.MAX_PAGE_SIZE}, null)),
    OUT_OF_PAGE_NUMBER(new ObjectError("", new String[]{"book.search.page.number.outOfRange"}, new Object[]{BookRequestCond.MIN_PAGE_NUMBER,BookRequestCond.MAX_PAGE_NUMBER}, null));

    private final ObjectError objectError;

    BookBindingError(ObjectError objectError) {
        this.objectError = objectError;
    }

    public ObjectError error() {
        return objectError;
    }
}
