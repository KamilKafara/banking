package com.prime.banking.app.exception;

import com.prime.banking.app.exception.handler.FieldInfo;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Getter
public class ValidationException extends RuntimeException {
    private Collection<FieldInfo> fields;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, FieldInfo fieldInfo) {
        super(message);
        this.fields = Collections.singletonList(fieldInfo);
    }
}

