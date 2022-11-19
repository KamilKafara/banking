package com.banking.account.exchange.rates;

import com.banking.account.exchange.rates.utils.CurrencyDTO;
import com.banking.account.exchange.rates.utils.CurrencyType;
import com.banking.account.exchange.rates.utils.ExchangeType;
import com.banking.account.exchange.rates.utils.TableDTO;

import java.io.IOException;
import java.util.List;

public interface ExchangeApi {
    List<TableDTO> getSerializedData(ExchangeType type) throws IOException;

    CurrencyDTO getSerializedData(ExchangeType type, CurrencyType currencyType) throws IOException;
}
