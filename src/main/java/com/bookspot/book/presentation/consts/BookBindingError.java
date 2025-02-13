package com.bookspot.book.presentation.consts;

import org.springframework.validation.ObjectError;

public enum BookBindingError {
    SEARCH_CRITERIA_MISSING(new ObjectError("", new String[]{"book.search.criteria.missing"}, null, null)),
    TOO_LARGE_PAGE_SIZE(new ObjectError("", new String[]{"book.search.page.size.tooLarge"}, new Object[]{BookRequestCond.MAX_SEARCH_PAGE_SIZE}, null));

    private final ObjectError objectError;

    BookBindingError(ObjectError objectError) {
        this.objectError = objectError;
    }

    public ObjectError error() {
        return objectError;
    }
}
