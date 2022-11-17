package com.prime.banking.app.exception;

import com.prime.banking.app.exception.handler.FieldInfo;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Getter
public class NotFoundException extends RuntimeException {
    private Collection<FieldInfo> fields;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, FieldInfo fieldInfo) {
        super(message);
        this.fields = Collections.singletonList(fieldInfo);
    }

}

