package com.banking.account.exchange.rates.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyDTO {
    private String currency;
    private String code;
    private BigDecimal mid;

    private static final String CURRENCY_PLN_NAME = "Polski złoty";

    public static CurrencyDTO initDefaultCurrency() {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setCurrency(CURRENCY_PLN_NAME);
        currencyDTO.setMid(BigDecimal.ONE);
        currencyDTO.setCode(CurrencyType.PLN.name());
        return currencyDTO;
    }

}
