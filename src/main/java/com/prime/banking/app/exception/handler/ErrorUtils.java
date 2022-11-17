package com.prime.banking.app.exception.handler;

import com.prime.banking.app.exception.NotFoundException;
import com.prime.banking.app.exception.ValidationException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorUtils {

    public static void notFoundException(String message) {
        throw new NotFoundException(message);
    }

    public static void notFoundException(String message, FieldInfo fieldInfo) {
        throw new NotFoundException(message, fieldInfo);
    }

    public static void validationException(String message) {
        throw new ValidationException(message);
    }

    public static void validationException(String message, FieldInfo fieldInfo) {
        throw new ValidationException(message, fieldInfo);
    }
}

