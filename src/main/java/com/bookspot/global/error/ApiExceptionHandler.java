package com.bookspot.global.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> bindingExceptionHandler(BindException e, Locale locale) {
        Set<String> uniqueErrorMessages = getUniqueErrorMessages(e, locale);
        StringBuilder errorStringBuilder = createMessageString(uniqueErrorMessages);
        return new ResponseEntity<>(new ErrorResponse(errorStringBuilder.toString()), HttpStatus.BAD_REQUEST);
    }

    private StringBuilder createMessageString(Set<String> uniqueErrorMessages) {
        StringBuilder errorStringBuilder = new StringBuilder();
        for (String uniqueErrorMessage : uniqueErrorMessages) {
            errorStringBuilder.append(uniqueErrorMessage).append("\n");
        }
        return errorStringBuilder;
    }

    private Set<String> getUniqueErrorMessages(BindException e, Locale locale) {
        Set<String> uniqueErrorMessages = new HashSet<>();
        for (ObjectError error : e.getAllErrors()) {
            String errorMessage = messageSource.getMessage(error, locale);
            uniqueErrorMessages.add(errorMessage);
        }
        return uniqueErrorMessages;
    }
}
