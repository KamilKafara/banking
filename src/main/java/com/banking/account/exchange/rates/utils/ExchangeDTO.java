package com.banking.account.exchange.rates.utils;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeDTO {
    private ExchangeType exchangeType;
    private CurrencyType currencySource;
    private BigDecimal sourceValue;
    private CurrencyType currencyTarget;
    private BigDecimal targetValue;
}
