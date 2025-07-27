package com.bookspot.category.presentation;

import org.springframework.validation.ObjectError;

public enum CategoryBindingError {
    OUT_OF_CATEGORY_ID(new ObjectError("", new String[]{"category.search.id.outOfRange"}, new Object[]{CategoryRequestCond.MIN_CATEGORY_ID, CategoryRequestCond.MAX_CATEGORY_ID}, null));

    private final ObjectError objectError;

    CategoryBindingError(ObjectError objectError) {
        this.objectError = objectError;
    }

    public ObjectError error() {
        return objectError;
    }
}
