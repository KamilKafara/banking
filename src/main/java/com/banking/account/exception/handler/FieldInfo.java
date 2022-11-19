package com.banking.account.exception.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Builder
@Setter
@Getter
public class FieldInfo {
    private String name;
    private String message;
    private ErrorCode errorCode;
    private HttpStatus httpStatus;

    public FieldInfo(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public FieldInfo(String name, String message, ErrorCode errorCode) {
        this.name = name;
        this.message = message;
        this.errorCode = errorCode;
    }

    public FieldInfo(String name, String message, ErrorCode errorCode, HttpStatus httpStatus) {
        this.name = name;
        this.message = message;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public FieldInfo(String name, ErrorCode errorCode) {
        this.name = name;
        this.errorCode = errorCode;
    }
}
