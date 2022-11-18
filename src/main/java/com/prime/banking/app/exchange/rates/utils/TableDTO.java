package com.prime.banking.app.exchange.rates.utils;

import com.prime.banking.app.exchange.rates.utils.CurrencyDTO;
import com.prime.banking.app.exchange.rates.utils.ExchangeType;

import java.util.List;

class TableDTO {
    private ExchangeType table;
    private String no;
    private String effectiveDate;
    private List<CurrencyDTO> rates;
}
