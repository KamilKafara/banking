package com.banking.account.dto;

import com.banking.account.exchange.rates.utils.CurrencyType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class AccountDTO {
    private Long id;
    private CurrencyType accountCurrencyType;
    private Map<CurrencyType, BigDecimal> foundsInDifferentCurrencies;
    private BigDecimal currentBalance;
    private UserDTO userDTO;

    public AccountDTO() {
        this.accountCurrencyType = CurrencyType.PLN;
    }

    public AccountDTO(Long id, UserDTO userDTO, BigDecimal currentBalance) {
        this.id = id;
        this.userDTO = userDTO;
        this.accountCurrencyType = CurrencyType.PLN;
        this.currentBalance = currentBalance;
    }
}
