package com.prime.banking.app.exchange.rates;

import com.google.gson.Gson;
import com.prime.banking.app.exchange.rates.utils.ExchangeType;
import com.prime.banking.app.exchange.rates.utils.TableDTOList;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ExchangeApiImpl {
    private static final String API_URL = "https://api.nbp.pl/api/exchangerates/tables/";

    public List<TableDTOList> getSerializedData(ExchangeType type) throws IOException {
        String data = fetchData(type);
        return new Gson().fromJson(data, List.class);
    }

    private String fetchData(ExchangeType type) throws IOException {
        URL url = new URL(API_URL + type.name());
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.0.11)");
        InputStream inputFile = urlConnection.getInputStream();
        return new String(inputFile.readAllBytes(), StandardCharsets.UTF_8);
    }
}
