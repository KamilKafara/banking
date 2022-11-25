package com.banking.account.utils.mapper;

import com.banking.account.dto.AccountDTO;
import com.banking.account.repository.AccountEntity;

public class AccountMapper {

    public static AccountEntity fromDTO(AccountDTO accountDTO) {
        AccountEntity account = new AccountEntity();
        account.setId(accountDTO.getId());
        account.setCurrencyType(accountDTO.getAccountCurrencyType());
        account.setCurrentBalance(accountDTO.getCurrentBalance());
        if (accountDTO.getUser() != null) {
            account.setUser(UserMapper.fromDTO(accountDTO.getUser()));
        }
        return account;
    }

    public static AccountDTO toDTO(AccountEntity account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountCurrencyType(accountDTO.getAccountCurrencyType());
        accountDTO.setCurrentBalance(account.getCurrentBalance());
        if (account.getUser() != null) {
            accountDTO.setUser(UserMapper.toDTO(account.getUser()));
        }
        return accountDTO;
    }
}
