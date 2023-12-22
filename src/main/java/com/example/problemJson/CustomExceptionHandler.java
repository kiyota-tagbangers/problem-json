package com.example.problemJson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author kiyota
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        log.error("exception occurs", ex);
        List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(ErrorDetail::new)
                .toList();
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    record ErrorDetail(String field, Object rejectedValue, String code, String message) {
        ErrorDetail(FieldError error)  {
            this(error.getField(), error.getRejectedValue(), error.getCode(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    ProblemDetail handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) {
        log.error("exception occurs", ex);
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setDetail(ex.getMessage());
        // problemDetail.setProperty("cause", ex);
        return problemDetail;
    }
}
