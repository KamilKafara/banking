package com.banking.account.exchange.rates.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum CurrencyType implements Predicate<CurrencyType> {
    PLN(true),
    USD(true),
    EUR(true),
    HUF(true);

    private final boolean isSupported;

    CurrencyType(boolean isSupported) {
        this.isSupported = isSupported;
    }

    public boolean isSupported() {
        return isSupported;
    }

    public static List<CurrencyType> getSupportedCurrencyTypes() {
        return Arrays.stream(values())
                .filter(CurrencyType::isSupported)
                .collect(Collectors.toList());
    }

    @Override
    public boolean test(CurrencyType currencyType) {
        return this.equals(currencyType);
    }
}
