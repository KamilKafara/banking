package com.banking.account.exchange.rates;

import com.banking.account.exception.NotFoundException;
import com.banking.account.exchange.rates.utils.CurrencyDTO;
import com.banking.account.exchange.rates.utils.CurrencyType;
import com.banking.account.exchange.rates.utils.ExchangeType;
import com.banking.account.exchange.rates.utils.TableDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeApiImpl implements ExchangeApi {
    private static final String API_URL = "https://api.nbp.pl/api/exchangerates/tables/";

    public List<TableDTO> getSerializedData(ExchangeType type) throws IOException {
        String data = fetchData(type);
        final ObjectMapper objectMapper = new ObjectMapper();
        TableDTO[] array = objectMapper.readValue(data, TableDTO[].class);
        return List.of(array);
    }

    private String fetchData(ExchangeType type) throws IOException {
        URL url = new URL(API_URL + type.name());
        URLConnection urlConnection = url.openConnection();
        InputStream inputFile = urlConnection.getInputStream();
        return new String(inputFile.readAllBytes(), StandardCharsets.UTF_8);
    }

    public CurrencyDTO getSerializedData(ExchangeType type, CurrencyType currencyType) throws IOException {
        List<TableDTO> list = getSerializedData(type);
        Optional<TableDTO> item = list.stream().findFirst();
        if (item.isEmpty()) {
            throw new NotFoundException("Not found any data");
        }

        Optional<CurrencyDTO> currencyDTOS = item.get().getRates().stream()
                .filter(it -> it.getCode().equalsIgnoreCase(currencyType.name()))
                .findFirst();
        if (currencyDTOS.isEmpty()) {
            throw new NotFoundException("Not data with this currencyType {" + currencyType.name() + "}.");
        }
        return currencyDTOS.get();
    }
}