package com.banking.account.dto;

import com.banking.account.exchange.rates.utils.CurrencyType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private Long id;
    private CurrencyType currencyType;
    private BigDecimal currentBalance;
    private BigDecimal initialAccountBalance;
    private UserDTO userDTO;

    public AccountDTO() {
        this.currencyType = CurrencyType.PLN;
    }

    public AccountDTO(Long id, UserDTO userDTO, BigDecimal currentBalance, BigDecimal initialAccountBalance) {
        this.id = id;
        this.userDTO = userDTO;
        this.currencyType = CurrencyType.PLN;
        this.currentBalance = currentBalance;
        this.initialAccountBalance = initialAccountBalance;
    }
}
