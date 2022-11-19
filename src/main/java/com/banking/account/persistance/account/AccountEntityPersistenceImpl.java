package com.banking.account.persistance.account;

import com.banking.account.dto.AccountDTO;
import com.banking.account.exception.NotFoundException;
import com.banking.account.repository.AccountEntity;
import com.banking.account.repository.AccountEntityRepository;
import com.banking.account.utils.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountEntityPersistenceImpl implements AccountEntityPersistence {

    private final AccountEntityRepository accountEntityRepository;

    @Autowired
    public AccountEntityPersistenceImpl(AccountEntityRepository accountEntityRepository) {
        this.accountEntityRepository = accountEntityRepository;
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountEntityRepository.findAll().stream()
                .map(AccountMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Optional<AccountEntity> accountEntity = accountEntityRepository.findById(id);
        if (accountEntity.isEmpty()) {
            throw new NotFoundException("Not found account with this id {" + id + "}.");
        }
        return AccountMapper.toDTO(accountEntity.get());
    }

    @Override
    public AccountDTO getAccountByUserId(Long userId) {
        Optional<AccountEntity> accountEntity = accountEntityRepository.findAccountByUserId(userId);
        if (accountEntity.isEmpty()) {
            throw new NotFoundException("Not found account with this id {" + userId + "}.");
        }
        return AccountMapper.toDTO(accountEntity.get());
    }

    @Override
    public AccountDTO getAccountByUserPesel(Long pesel) {
        Optional<AccountEntity> accountEntity = accountEntityRepository.findAccountByUserPesel(pesel);
        if (accountEntity.isEmpty()) {
            throw new NotFoundException("Not found account assigned to user with this pesel {" + pesel + "}.");
        }
        return AccountMapper.toDTO(accountEntity.get());
    }

    @Override
    public AccountDTO save(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
