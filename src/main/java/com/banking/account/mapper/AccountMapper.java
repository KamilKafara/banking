package com.banking.account.mapper;

import com.banking.account.dto.AccountDTO;
import com.banking.account.repository.AccountEntity;
import org.modelmapper.ModelMapper;

public class AccountMapper {
    public static AccountEntity fromDTO(AccountDTO accountDTO) {
        return new ModelMapper().map(accountDTO, AccountEntity.class);
    }

    public static AccountDTO toDTO(AccountEntity account) {
        return new ModelMapper().map(account, AccountDTO.class);
    }
}
