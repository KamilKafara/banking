package com.banking.account.dto;

import com.banking.account.exchange.rates.utils.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
@EqualsAndHashCode(exclude = "user")
public class AccountDTO {
    private Long id;
    private CurrencyType accountCurrencyType;
    private Map<CurrencyType, BigDecimal> foundsInDifferentCurrencies;
    private BigDecimal currentBalance;
    @JsonIgnoreProperties("accounts")
    private UserDTO user;

    public AccountDTO() {
        this.foundsInDifferentCurrencies = new HashMap<>();
        this.accountCurrencyType = CurrencyType.PLN;
    }

    public AccountDTO(Long id, UserDTO user, BigDecimal currentBalance) {
        this.id = id;
        this.user = user;
        this.accountCurrencyType = CurrencyType.PLN;
        this.currentBalance = currentBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CurrencyType getAccountCurrencyType() {
        return accountCurrencyType;
    }

    public void setAccountCurrencyType(CurrencyType accountCurrencyType) {
        this.accountCurrencyType = accountCurrencyType;
    }

    public Map<CurrencyType, BigDecimal> getFoundsInDifferentCurrencies() {
        return foundsInDifferentCurrencies;
    }

    public void setFoundsInDifferentCurrencies(Map<CurrencyType, BigDecimal> foundsInDifferentCurrencies) {
        this.foundsInDifferentCurrencies = foundsInDifferentCurrencies;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
