package com.prime.banking.app.exchange.rates.utils;

import lombok.Data;

import java.math.BigDecimal;

@Data
class CurrencyDTO {
    private String currency;
    private String code;
    private BigDecimal mid;
}
