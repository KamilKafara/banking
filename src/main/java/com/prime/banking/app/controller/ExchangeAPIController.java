package com.prime.banking.app.controller;

import com.prime.banking.app.exchange.rates.ExchangeApiImpl;
import com.prime.banking.app.exchange.rates.utils.ExchangeType;
import com.prime.banking.app.exchange.rates.utils.TableDTOList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/nbp")
class ExchangeAPIController {
    private final ExchangeApiImpl exchangeApi;

    ExchangeAPIController(ExchangeApiImpl exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @GetMapping("/{type}")
    public List<TableDTOList> getSerializedData(@PathVariable("type") ExchangeType type) throws IOException {
        return exchangeApi.getSerializedData(type);
    }
}
