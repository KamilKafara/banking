package com.banking.account.persistance.account;

import com.banking.account.dto.AccountDTO;
import com.banking.account.dto.UserDTO;
import com.banking.account.exception.NotFoundException;
import com.banking.account.exception.ValidationException;
import com.banking.account.exception.handler.ErrorCode;
import com.banking.account.exception.handler.FieldInfo;
import com.banking.account.exchange.rates.ExchangeApi;
import com.banking.account.exchange.rates.utils.CurrencyType;
import com.banking.account.exchange.rates.utils.ExchangeDTO;
import com.banking.account.exchange.rates.utils.ExchangeType;
import com.banking.account.persistance.user.UserEntityPersistence;
import com.banking.account.repository.AccountEntity;
import com.banking.account.repository.AccountEntityRepository;
import com.banking.account.repository.UserEntity;
import com.banking.account.utils.mapper.AccountMapper;
import com.banking.account.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountEntityPersistenceImpl implements AccountEntityPersistence {

    private final AccountEntityRepository accountEntityRepository;
    private final UserEntityPersistence userEntityPersistence;
    private final ExchangeApi exchangeApi;
    private final AccountMapper accountMapper;
    private final UserMapper userMapper;

    @Autowired
    public AccountEntityPersistenceImpl(AccountEntityRepository accountEntityRepository, @Lazy UserEntityPersistence userEntityPersistence, ExchangeApi exchangeApi, AccountMapper accountMapper, UserMapper userMapper) {
        this.accountEntityRepository = accountEntityRepository;
        this.userEntityPersistence = userEntityPersistence;
        this.exchangeApi = exchangeApi;
        this.accountMapper = accountMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountEntityRepository.findAll().stream()
                .map(accountMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Optional<AccountEntity> accountEntity = accountEntityRepository.findById(id);
        if (accountEntity.isEmpty()) {
            throw new NotFoundException("Not found account with this id {" + id + "}.");
        }
        AccountDTO accountDTO = accountMapper.toDTO(accountEntity.get());
        setupSupportedCurrencies(accountDTO);
        accountDTO.setUser(userEntityPersistence.getById(id));
        return accountDTO;
    }

    @Override
    public void setupSupportedCurrencies(AccountDTO accountDTO) {
        List<CurrencyType> supportedCurrencyTypes = CurrencyType.getSupportedCurrencyTypes();
        Map<CurrencyType, BigDecimal> currencies = new LinkedHashMap<>();
        supportedCurrencyTypes.forEach(type -> {
            try {
                ExchangeDTO exchangeRate = exchangeApi.exchangeRate(
                        ExchangeType.A,
                        accountDTO.getAccountCurrencyType(),
                        type,
                        accountDTO.getCurrentBalance());
                currencies.put(type, exchangeRate.getTargetValue());
            } catch (IOException e) {
                throw new ValidationException("An error occurred: " + e.getMessage());
            }
        });
        accountDTO.setEquivalentInOtherCurrencies(currencies);
    }

    @Override
    public AccountDTO getAccountByUserId(Long userId) {
        Optional<AccountEntity> accountEntity = accountEntityRepository.findAccountByUserId(userId);
        if (accountEntity.isEmpty()) {
            throw new NotFoundException("Not found account with this id {" + userId + "}.");
        }
        return accountMapper.toDTO(accountEntity.get());
    }

    @Override
    public AccountDTO getAccountByUserPesel(String pesel) {
        Optional<AccountEntity> accountEntity = accountEntityRepository.findAccountByUserPesel(pesel);
        if (accountEntity.isEmpty()) {
            throw new NotFoundException("Not found account assigned to user with this pesel {" + pesel + "}.");
        }
        return accountMapper.toDTO(accountEntity.get());
    }

    @Override
    public AccountDTO save(AccountDTO accountDTO) {
        validateAccountToSave(accountDTO);

        UserEntity userEntity = userMapper.fromDTO(userEntityPersistence.save(accountDTO.getUser()));
        AccountEntity accountWithUserToSave = prepareAccountDataToSave(accountDTO, userEntity);
        AccountEntity accountEntity = accountEntityRepository.save(accountWithUserToSave);
        return prepareDTOData(userEntity, accountEntity);
    }

    private void validateAccountToSave(AccountDTO accountDTO) {
        if (accountDTO.getId() != null) {
            throw new ValidationException("Id must be null.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        if (accountDTO.getCurrentBalance() == null || accountDTO.getCurrentBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Balance cannot be negative", new FieldInfo("currentBalance", ErrorCode.BAD_REQUEST));
        }
        if (Objects.isNull(accountDTO.getUser())) {
            throw new ValidationException("Cannot create account without user data.", new FieldInfo("userDTO", ErrorCode.BAD_REQUEST));
        }

        Optional<AccountEntity> optionalAccountEntity = accountEntityRepository.findAccountByUserPesel(accountDTO.getUser().getPesel());
        if (optionalAccountEntity.isPresent()) {
            throw new ValidationException("This account is already assigned to user with this pesel.", new FieldInfo("userDTO", ErrorCode.BAD_REQUEST));
        }
    }

    private AccountEntity prepareAccountDataToSave(AccountDTO accountDTO, UserEntity userEntity) {
        AccountEntity acountToSave = accountMapper.fromDTO(accountDTO);
        acountToSave.setUser(userEntity);
        return acountToSave;
    }

    private AccountDTO prepareDTOData(UserEntity userEntity, AccountEntity accountEntity) {
        AccountDTO result = accountMapper.toDTO(accountEntity);
        result.setUser(userMapper.toDTO(userEntity));
        return result;
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO, Long id) {
        if (!id.equals(accountDTO.getId())) {
            throw new ValidationException("Ids are not equals.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        if (accountDTO.getCurrentBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Balance cannot be negative", new FieldInfo("currentBalance", ErrorCode.BAD_REQUEST));
        }
        getAccountById(id);
        AccountEntity account = accountMapper.fromDTO(accountDTO);
        accountDTO.setId(account.getId());
        AccountEntity updatedAccount = accountEntityRepository.save(account);
        UserDTO updatedUser = userEntityPersistence.update(accountDTO.getUser(), updatedAccount.getId());
        AccountDTO result = accountMapper.toDTO(updatedAccount);
        result.setUser(updatedUser);
        return result;
    }

    @Override
    public void delete(Long id) {
        AccountDTO currentAccount = getAccountById(id);
        if (BigDecimal.ZERO.equals(currentAccount.getCurrentBalance())) {
            throw new ValidationException("Cannot delete account with not null balance.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        accountEntityRepository.delete(accountMapper.fromDTO(currentAccount));
    }

    public AccountDTO exchangeMoney(Long userId,
                                    ExchangeType exchangeType,
                                    CurrencyType currentCurrencyType,
                                    CurrencyType targetCurrencyType,
                                    BigDecimal amountToExchange) throws IOException {

        AccountDTO accountDTO = getAccountByUserId(userId);
        if (!accountDTO.getAccountCurrencyType().equals(currentCurrencyType)) {
            throw new ValidationException("CurrencyType are not equals.", new FieldInfo("currentCurrencyType", ErrorCode.BAD_REQUEST));
        }
        ExchangeDTO newBalance = exchangeApi.exchangeRate(exchangeType, targetCurrencyType, targetCurrencyType, amountToExchange);
        accountDTO.setCurrentBalance(newBalance.getTargetValue());
        accountDTO.setAccountCurrencyType(targetCurrencyType);
        return update(accountDTO, accountDTO.getId());

    }
}
