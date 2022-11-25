package com.banking.account.exchange.rates;

import com.banking.account.exception.NotFoundException;
import com.banking.account.exception.ValidationException;
import com.banking.account.exchange.rates.utils.CurrencyDTO;
import com.banking.account.exchange.rates.utils.CurrencyType;
import com.banking.account.exchange.rates.utils.ExchangeType;
import com.banking.account.exchange.rates.utils.TableDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeApiImpl implements ExchangeApi {
    private static final String API_URL = "https://api.nbp.pl/api/exchangerates/tables/";

    public List<TableDTO> getCurrencyByType(ExchangeType type) throws IOException {
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

    public CurrencyDTO getCurrencyByType(ExchangeType type, CurrencyType currencyType) throws IOException {
        List<TableDTO> list = getCurrencyByType(type);
        Optional<TableDTO> item = list.stream().findFirst();
        if (item.isEmpty()) {
            throw new NotFoundException("Not found any data");
        }
        if(CurrencyType.PLN.test(currencyType)){
            CurrencyDTO currencyDTO = new CurrencyDTO();
            currencyDTO.setCurrency("Polski złoty");
            currencyDTO.setMid(BigDecimal.ONE);
            currencyDTO.setCode(currencyType.name());
            return currencyDTO;
        }
        Optional<CurrencyDTO> currencyDTOS = item.get().getRates().stream()
                .filter(it -> it.getCode().equalsIgnoreCase(currencyType.name()))
                .findFirst();
        if (currencyDTOS.isEmpty()) {
            throw new NotFoundException("Not data with this currencyType {" + currencyType.name() + "}.");
        }
        return currencyDTOS.get();
    }

    @Override
    public BigDecimal exchangeRate(ExchangeType type,
                                   CurrencyType source,
                                   CurrencyType target,
                                   BigDecimal basicValue) throws IOException {
        BigDecimal targetValue = BigDecimal.ONE;
        BigDecimal sourceValue = BigDecimal.ONE;

        if (!CurrencyType.PLN.test(source)) {
            sourceValue = getCurrencyByType(type, source).getMid();
        }
        if (!CurrencyType.PLN.test(target)) {
            targetValue = getCurrencyByType(type, target).getMid();
        }
        BigDecimal exchangeRate = sourceValue.divide(targetValue, 2, RoundingMode.UP);
        return exchangeRate.multiply(basicValue);
    }
}
