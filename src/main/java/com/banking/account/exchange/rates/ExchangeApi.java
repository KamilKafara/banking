package com.banking.account.exchange.rates;

import com.banking.account.exchange.rates.utils.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ExchangeApi {
    List<TableDTO> getCurrencyByType(ExchangeType type) throws IOException;

    CurrencyDTO getCurrencyByType(ExchangeType type, CurrencyType currencyType) throws IOException;

    ExchangeDTO exchangeRate(ExchangeType type, CurrencyType currencyType, CurrencyType targetCurrencyType, BigDecimal valueToConvert) throws IOException;

}
