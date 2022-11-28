package com.banking.account.repository;

import com.banking.account.exchange.rates.utils.CurrencyType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link AccountEntity} entity
 */
@Data
public class AccountEntityDto implements Serializable {
    private final Long id;
    private final CurrencyType currencyType;
    private final BigDecimal currentBalance;
    private final UserEntityDto user;

    /**
     * A DTO for the {@link UserEntity} entity
     */
    @Data
    public static class UserEntityDto implements Serializable {
        private final Long id;
        private final String firstName;
        private final String secondName;
        private final Long pesel;
    }
}
