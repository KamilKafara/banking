package com.banking.account.controller;

import com.banking.account.exchange.rates.ExchangeApi;
import com.banking.account.exchange.rates.utils.CurrencyDTO;
import com.banking.account.exchange.rates.utils.CurrencyType;
import com.banking.account.exchange.rates.utils.ExchangeType;
import com.banking.account.exchange.rates.utils.TableDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/exchange")
public class ExchangeAPIController {
    private final ExchangeApi exchangeApi;

    ExchangeAPIController(ExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @GetMapping("/{type}")
    public List<TableDTO> getSerializedData(@PathVariable("type") ExchangeType type) throws IOException {
        return exchangeApi.getSerializedData(type);
    }

    @GetMapping("/{type}/{currency}")
    public CurrencyDTO getSerializedData(@PathVariable("type") ExchangeType type, @PathVariable("currency") CurrencyType currencyType) throws IOException {
        return exchangeApi.getSerializedData(type, currencyType);
    }
}
