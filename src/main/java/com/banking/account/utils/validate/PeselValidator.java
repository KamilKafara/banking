package com.banking.account.utils.validate;

import com.banking.account.exception.ValidationException;
import com.banking.account.exception.handler.ErrorCode;
import com.banking.account.exception.handler.FieldInfo;
import org.hibernate.validator.internal.constraintvalidators.hv.pl.PESELValidator;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class PeselValidator {
    private static final String NOT_VALID_LENGTH = "PESEL length hasn't correct length. Valid length is 11.";
    private static final String USER_AGE_NOT_VALID = "User with this PESEL number is not adult.";
    private static final String NOT_VALID = "Pesel is not valid.";
    private static final int ADULT_AGE = 18;

    private PeselValidator() {
    }

    private static final int PESEL_LENGTH = 11;

    public static void validate(String pesel) {
        List<String> errors = new ArrayList<>();
        if (validateLength(pesel)) {
            errors.add(NOT_VALID_LENGTH);
        }

        PESELValidator validator = new PESELValidator();
        validator.initialize(null);
        if (!validator.isValid(pesel, null)) {
            errors.add(NOT_VALID);
        }

        if (!validateAge(pesel)) {
            errors.add(USER_AGE_NOT_VALID);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Pesel is not valid for this reason:" + errors, new FieldInfo("pesel", ErrorCode.BAD_REQUEST));
        }
    }

    private static boolean validateAge(String pesel) {
        LocalDate birthDate = getBirthDateByPesel(pesel);
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);

        return period.getYears() > ADULT_AGE;
    }

    public static LocalDate getBirthDateByPesel(String pesel) {
        int year = Integer.parseInt(pesel.substring(0, 2));
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        if (month > 80) {
            year = 1800 + year;
            month = month - 80;
        } else if (month > 60) {
            year = 2200 + year;
            month = month - 60;
        } else if (month > 40) {
            year = 2100 + year;
            month = month - 40;
        } else if (month > 20) {
            year = 2000 + year;
            month = month - 20;
        } else {
            year = 1900 + year;
        }

        return LocalDate.of(year, month, day);
    }

    public static boolean validateLength(String pesel) {
        return pesel.length() != PESEL_LENGTH;
    }
}
