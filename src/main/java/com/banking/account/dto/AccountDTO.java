package com.banking.account.dto;

import com.banking.account.exchange.rates.utils.CurrencyType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private Long id;
    private CurrencyType currencyType;
    private BigDecimal value;
}
