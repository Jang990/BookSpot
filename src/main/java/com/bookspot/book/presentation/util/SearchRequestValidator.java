package com.bookspot.book.presentation.util;

import com.bookspot.book.presentation.consts.BookBindingError;
import com.bookspot.book.presentation.consts.BookRequestCond;
import com.bookspot.book.presentation.request.BookSearchRequest;
import com.bookspot.book.presentation.request.BookSort;
import com.bookspot.book.presentation.request.CategoryLevel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@Service
public class SearchRequestValidator {
    public static void validateCategoryCond(
            Integer categoryId,
            CategoryLevel categoryLevel,
            BindingResult bindingResult
    ) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);

        if(categoryId == null && categoryLevel == null)
            return;
        if(categoryId != null && categoryLevel != null)
            return;

        bindingResult.addError(BookBindingError.SEARCH_CATEGORY_INVALID.error());
        throw new BindException(bindingResult);
    }

    public static void validatePageable(
            Pageable pageable, BindingResult bindingResult
    ) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);

        int pageNumber = pageable.getPageNumber();
        // pageable은 디폴트 값을 채워줌 size는 20 number는 0
        if (/*pageNumber < BookRequestCond.MIN_PAGE_NUMBER
                || */BookRequestCond.MAX_PAGE_NUMBER < pageNumber) {
            bindingResult.addError(BookBindingError.OUT_OF_PAGE_NUMBER.error());
            throw new BindException(bindingResult);
        }

        validatePageSize(pageable.getPageSize(), bindingResult);
    }

    public static void validatePageSize(int pageSize, BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);
        if (/*pageSize < BookRequestCond.MIN_PAGE_SIZE
                || */BookRequestCond.MAX_PAGE_SIZE < pageSize) {
            bindingResult.addError(BookBindingError.OUT_OF_PAGE_SIZE.error());
            throw new BindException(bindingResult);
        }
    }

    public static void validateSortByRelevance(
            BookSearchRequest request,
            BindingResult bindingResult
    ) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);

        if (request.getSortBy() == BookSort.RELEVANCE && request.getTitle() == null) {
            bindingResult.addError(BookBindingError.SEARCH_RELEVANCE_SORT_CRITERIA_INVALID.error());
            throw new BindException(bindingResult);
        }
    }

    public static boolean isNotNumeric(String strNum) {
        try {
            double num = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }

    public static void validateNumericScore(String lastScore, BindingResult bindingResult) throws BindException {
        if (isNotNumeric(lastScore)) {
            bindingResult.addError(BookBindingError.SEARCH_AFTER_INVALID_SCORE.error());
            throw new BindException(bindingResult);
        }
    }
}
