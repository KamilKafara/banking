package com.banking.account.utils.validate;

import com.banking.account.exception.ValidationException;
import com.banking.account.exception.handler.ErrorCode;
import com.banking.account.exception.handler.FieldInfo;

import java.util.ArrayList;
import java.util.List;

public class PeselValidator {
    private static final String NOT_VALID_LENGTH = "PESEL length hasn't correct length. Valid length is 11.";
    private static final String USER_AGE_NOT_VALID = "User with this PESEL number is not adult.";

    private PeselValidator() {
    }

    private static final int PESEL_LENGHT = 11;

    public static void validate(Long pesel) {
        List<String> errors = new ArrayList<>();
        if (validate(String.valueOf(pesel))) {
            errors.add(NOT_VALID_LENGTH);
        }
        if (validateAge(String.valueOf(pesel))) {
            errors.add(USER_AGE_NOT_VALID);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Pesel is not valid for this reason:" + errors, new FieldInfo("pesel", ErrorCode.BAD_REQUEST));
        }
    }

    private static boolean validateAge(String pesel) {
        return false;
    }

    public static boolean validate(String pesel) {
        return pesel.length() != PESEL_LENGHT;
    }
}
