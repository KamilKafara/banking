package com.banking.account.exchange.rates.utils;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * current and historic exchange rates of foreign currencies:
 */
@AllArgsConstructor
public enum ExchangeType {
    /**
     * table A of middle exchange rates of foreign currencies,
     */
    A(1),
    /**
     * table B of middle exchange rates of foreign currencies,
     */
    B(2),
    /**
     * able C of buy and sell prices of foreign currencies;
     */
    C(3);

    private final int id;

    public static final Map<String, ExchangeType> types = new HashMap<>();

    static {
        Arrays.stream(ExchangeType.values())
                .forEach(type -> types.put(type.name(), type));
    }
}
