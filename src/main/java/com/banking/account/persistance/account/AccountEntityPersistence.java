package com.banking.account.persistance.account;

import com.banking.account.dto.AccountDTO;

import java.util.List;

public interface AccountEntityPersistence {
    List<AccountDTO> getAllAccounts();

    AccountDTO getAccountById(Long id);

    AccountDTO getAccountByUserId(Long id);

    AccountDTO getAccountByUserPesel(String pesel);

    AccountDTO save(AccountDTO accountDTO);

    AccountDTO update(AccountDTO accountDTO, Long id);

    void delete(Long id);

    void setupSupportedCurrencies(AccountDTO accountDTO);
}
