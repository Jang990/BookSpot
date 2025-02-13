package com.bookspot.book.presentation;

import com.bookspot.book.presentation.consts.BookBindingError;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.List;

@Data
@AllArgsConstructor
public class BookSearchRequest {
    @Size(min = 2)
    private String title;
    private List<Long> bookIds;

    public boolean hasBookIds() {
        return bookIds != null && !bookIds.isEmpty();
    }

    public boolean hasTitle() {
        return title != null && !title.isBlank();
    }

    public boolean isCriteriaMissing() {
        return !hasTitle() && !hasBookIds();
    }
}
