package com.banking.account.controller;

import com.banking.account.exchange.rates.ExchangeApi;
import com.banking.account.exchange.rates.utils.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/exchange")
public class ExchangeAPIController {
    private final ExchangeApi exchangeApi;

    ExchangeAPIController(ExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @GetMapping("/{type}")
    public List<TableDTO> getCurrencyByType(@PathVariable("type") @DefaultValue("A") ExchangeType type) throws IOException {
        return exchangeApi.getCurrencyByType(type);
    }

    @GetMapping("/{type}/{currency}")
    public CurrencyDTO getCurrencyByType(@PathVariable("type") @DefaultValue("A") ExchangeType type,
                                         @PathVariable("currency") @DefaultValue("USD") CurrencyType currencyType) throws IOException {
        return exchangeApi.getCurrencyByType(type, currencyType);
    }

    @GetMapping("/exchange")
    public ExchangeDTO exchange(ExchangeType type,
                                CurrencyType sourceCurrencyType,
                                CurrencyType targetCurrencyType,
                                BigDecimal valueToConvert) throws IOException {
        return exchangeApi.exchangeRate(type, sourceCurrencyType, targetCurrencyType, valueToConvert);
    }
}
