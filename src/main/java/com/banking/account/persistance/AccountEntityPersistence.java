package com.banking.account.persistance;

import com.banking.account.dto.AccountDTO;

import java.util.List;

public interface AccountEntityPersistence {
    List<AccountDTO> getAllAccounts();

    AccountDTO getAccountById(Long id);

    AccountDTO getAccountByUserId(Long id);

    AccountDTO getAccountByUserPesel(Long pesel);

    AccountDTO save(AccountDTO accountDTO);

    AccountDTO update(AccountDTO accountDTO, Long id);

    void delete(Long id);
}
