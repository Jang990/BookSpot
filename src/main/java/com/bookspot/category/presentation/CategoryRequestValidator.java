package com.bookspot.category.presentation;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class CategoryRequestValidator {
    public static void validateCategoryId(int categoryId, BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);

        if (categoryId < CategoryRequestCond.MIN_CATEGORY_ID
                || CategoryRequestCond.MAX_CATEGORY_ID < categoryId) {
            bindingResult.addError(CategoryBindingError.OUT_OF_CATEGORY_ID.error());
            throw new BindException(bindingResult);
        }
    }
}
