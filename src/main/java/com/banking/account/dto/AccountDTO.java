package com.banking.account.dto;

import com.banking.account.exchange.rates.utils.CurrencyType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@EqualsAndHashCode(exclude = "user")
public class AccountDTO {
    private Long id;
    private CurrencyType accountCurrencyType;
    private BigDecimal currentBalance;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<CurrencyType, BigDecimal> equivalentInOtherCurrencies;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDTO user;

    public AccountDTO() {
        this.equivalentInOtherCurrencies = new HashMap<>();
        this.accountCurrencyType = CurrencyType.PLN;
    }

    public AccountDTO(Long id, UserDTO user, BigDecimal currentBalance) {
        this.id = id;
        this.user = user;
        this.accountCurrencyType = CurrencyType.PLN;
        this.currentBalance = currentBalance;
    }
}
