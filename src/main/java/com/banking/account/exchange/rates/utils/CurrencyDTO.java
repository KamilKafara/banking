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
}
