package com.banking.account.exception;

import com.banking.account.exception.handler.FieldInfo;
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

