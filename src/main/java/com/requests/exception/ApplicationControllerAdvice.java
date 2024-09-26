package com.requests.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(NumberRecordExecedLimit.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notValidException(NumberRecordExecedLimit ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundException(RecordNotFoundException rnfe) {
        return rnfe.getMessage();
    }
}
