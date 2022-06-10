package com.project.bookstore.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class BookstoreExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(final EntityNotFoundException exception) {
        return this.buildResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage(),
                Collections.singletonList(exception.getMessage()));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> handleEntityExistException(final EntityExistsException exception) {
        return this.buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage(),
                Collections.singletonList(exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {

        final List<String> errors = new ArrayList<>();
        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        final List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();

        fieldErrors.forEach(fieldError -> errors.add("Field " + fieldError.getField().toUpperCase() + " " + fieldError.getDefaultMessage()));
        globalErrors.forEach(globalError -> errors.add("Object " + globalError.getObjectName() + " " + globalError.getDefaultMessage()));

        return this.buildResponseEntity(HttpStatus.BAD_REQUEST, "Informed arguments(s) validation error(s)", errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {

        return this.buildResponseEntity(HttpStatus.BAD_REQUEST, "Malformed JSON body and/or field error(s)",
                Collections.singletonList(exception.getMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(final HttpStatus httpStatus, final String message,
                                                       final List<String> errors) {
        final ApiError apiError = ApiError.builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(httpStatus).body(apiError);
    }
}
