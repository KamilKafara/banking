package com.prime.banking.app.exchange.rates.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * current and historic exchange rates of foreign currencies:
 */
@Getter
@AllArgsConstructor
public
enum ExchangeType {
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
}
