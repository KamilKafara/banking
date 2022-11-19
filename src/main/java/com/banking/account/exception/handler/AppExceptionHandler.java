package com.banking.account.exception.handler;


import com.banking.account.exception.NotFoundException;
import com.banking.account.exception.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Log4j2
@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ResponseErrorInfo> handleNotFoundException(NotFoundException ex) {
        ResponseErrorInfo errorInfo = ResponseErrorInfo.builder()
                .message(ex.getMessage())
                .fields(ex.getFields())
                .build();
        log.error(ex);
        return ResponseEntity.status(NOT_FOUND).body(errorInfo);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ResponseErrorInfo> handleNotFoundException(ValidationException ex) {
        ResponseErrorInfo errorInfo = ResponseErrorInfo.builder()
                .message(ex.getMessage())
                .fields(ex.getFields())
                .build();
        log.error(ex);
        return ResponseEntity.status(BAD_REQUEST).body(errorInfo);
    }
}
