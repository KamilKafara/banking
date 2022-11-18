package com.prime.banking.app.utils.validate;

import com.prime.banking.app.exception.ValidationException;
import com.prime.banking.app.exception.handler.ErrorCode;
import com.prime.banking.app.exception.handler.FieldInfo;

import java.util.ArrayList;
import java.util.List;

public class PeselValidator {
    private static final String NOT_VALID_LENGTH = "PESEL length hasn't correct length. Valid length is 11.";
    private static final String USER_AGE_NOT_VALID = "User with this PESEL number is not adult.";

    private PeselValidator() {
    }

    private static final int PESEL_LENGHT = 11;

    public static boolean validateLength(Long pesel) {
        List<String> errors = new ArrayList<>();
        if (validateLength(String.valueOf(pesel))) {
            errors.add(NOT_VALID_LENGTH);
        }
        if (validateAge(String.valueOf(pesel))) {
            errors.add(USER_AGE_NOT_VALID);
        }
//        throw new ValidationException("Pesel is not valid for this reason:" + errors, new FieldInfo("pesel", ErrorCode.BAD_REQUEST));

        return errors.isEmpty();
    }

    private static boolean validateAge(String pesel) {
        return false;
    }

    public static boolean validateLength(String pesel) {
        return pesel.length() != PESEL_LENGHT;
    }
}
