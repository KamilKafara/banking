package com.banking.account.repository;

import com.banking.account.exchange.rates.utils.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "user")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private CurrencyType currencyType;
    private BigDecimal currentBalance;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonIgnoreProperties("accounts")
    private UserEntity user;
}
