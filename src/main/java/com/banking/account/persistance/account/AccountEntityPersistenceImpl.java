package com.banking.account.persistance.account;

import com.banking.account.dto.AccountDTO;
import com.banking.account.exception.NotFoundException;
import com.banking.account.exception.ValidationException;
import com.banking.account.exception.handler.ErrorCode;
import com.banking.account.exception.handler.FieldInfo;
import com.banking.account.exchange.rates.ExchangeApi;
import com.banking.account.exchange.rates.utils.CurrencyType;
import com.banking.account.exchange.rates.utils.ExchangeType;
import com.banking.account.repository.AccountEntity;
import com.banking.account.repository.AccountEntityRepository;
import com.banking.account.utils.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountEntityPersistenceImpl implements AccountEntityPersistence {

    private final AccountEntityRepository accountEntityRepository;
    private final ExchangeApi exchangeApi;

    @Autowired
    public AccountEntityPersistenceImpl(AccountEntityRepository accountEntityRepository, ExchangeApi exchangeApi) {
        this.accountEntityRepository = accountEntityRepository;
        this.exchangeApi = exchangeApi;
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
        if (accountDTO.getId() != null) {
            throw new ValidationException("Id must be null.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        if (!accountDTO.getCurrentBalance().equals(accountDTO.getInitialAccountBalance())) {
            throw new ValidationException("Values are not equal.", new FieldInfo("currentBalance / initialAccountBalance", ErrorCode.BAD_REQUEST));
        }
        AccountEntity accountEntity = accountEntityRepository.save(AccountMapper.fromDTO(accountDTO));
        return AccountMapper.toDTO(accountEntity);
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO, Long id) {
        if (!id.equals(accountDTO.getId())) {
            throw new ValidationException("Ids are not equals.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        AccountEntity account = AccountMapper.fromDTO(getAccountById(id));
        accountDTO.setId(account.getId());
        AccountEntity updatedAccount = accountEntityRepository.save(account);
        return AccountMapper.toDTO(updatedAccount);
    }

    @Override
    public void delete(Long id) {
        AccountDTO currentAccount = getAccountById(id);
        if (!Objects.equals(currentAccount.getCurrentBalance(), BigDecimal.ZERO)) {
            throw new ValidationException("Cannot delete account with activate balance.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        accountEntityRepository.delete(AccountMapper.fromDTO(currentAccount));
    }

    public AccountDTO exchangeMoney(Long userId,
                                    ExchangeType exchangeType,
                                    CurrencyType currentCurrencyType,
                                    CurrencyType targetCurrencyType,
                                    BigDecimal amountToExchange) throws IOException {

        AccountDTO accountDTO = getAccountByUserId(userId);
        if (!accountDTO.getCurrencyType().equals(currentCurrencyType)) {
            throw new ValidationException("CurrencyType are not equals.", new FieldInfo("currentCurrencyType", ErrorCode.BAD_REQUEST));
        }
        BigDecimal newBalance = exchangeApi.exchangeBalance(exchangeType, targetCurrencyType, amountToExchange);
        accountDTO.setCurrentBalance(newBalance);
        accountDTO.setCurrencyType(targetCurrencyType);
        return update(accountDTO, accountDTO.getId());

    }
}
