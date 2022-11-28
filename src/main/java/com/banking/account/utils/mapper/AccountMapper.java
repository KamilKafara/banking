package com.banking.account.utils.mapper;

import com.banking.account.dto.AccountDTO;
import com.banking.account.repository.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {

    private final UserMapper userMapper;

    @Autowired
    public AccountMapper(@Lazy UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public AccountEntity fromDTO(AccountDTO accountDTO) {
        AccountEntity account = new AccountEntity();
        account.setId(accountDTO.getId());
        account.setCurrencyType(accountDTO.getAccountCurrencyType());
        account.setCurrentBalance(accountDTO.getCurrentBalance());
        if (accountDTO.getUser() != null) {
            account.setUser(userMapper.fromDTO(accountDTO.getUser()));
        }
        return account;
    }

    public AccountDTO toDTO(AccountEntity account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountCurrencyType(account.getCurrencyType());
        accountDTO.setCurrentBalance(account.getCurrentBalance());
        return accountDTO;
    }
}
