package com.banking.account.persistance.user;

import com.banking.account.dto.AccountDTO;
import com.banking.account.dto.UserDTO;
import com.banking.account.exception.NotFoundException;
import com.banking.account.exception.ValidationException;
import com.banking.account.exception.handler.ErrorCode;
import com.banking.account.exception.handler.FieldInfo;
import com.banking.account.exchange.rates.ExchangeApi;
import com.banking.account.persistance.account.AccountEntityPersistence;
import com.banking.account.repository.AccountEntity;
import com.banking.account.repository.AccountEntityRepository;
import com.banking.account.repository.UserEntity;
import com.banking.account.repository.UserEntityRepository;
import com.banking.account.utils.mapper.AccountMapper;
import com.banking.account.utils.mapper.UserMapper;
import com.banking.account.utils.validate.PeselValidator;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class UserEntityPersistenceImpl implements UserEntityPersistence {
    private final UserEntityRepository userEntityRepository;
    private final AccountEntityRepository accountEntityRepository;
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;
    private final AccountEntityPersistence accountEntityPersistence;

    UserEntityPersistenceImpl(UserEntityRepository userEntityRepository, AccountEntityRepository accountEntityRepository, UserMapper userMapper, @Lazy AccountMapper accountMapper, AccountEntityPersistence accountEntityPersistence) {
        this.userEntityRepository = userEntityRepository;
        this.accountEntityRepository = accountEntityRepository;
        this.userMapper = userMapper;
        this.accountMapper = accountMapper;
        this.accountEntityPersistence = accountEntityPersistence;
    }

    @Override
    public List<UserDTO> getAllUser() {
        return userEntityRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(Long id) {
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("Not found user with this id {" + id + "}.");
        }
        Optional<AccountEntity> accountByUserId = accountEntityRepository.findAccountByUserId(id);
        UserDTO userDTO = userMapper.toDTO(userEntity.get());
        accountByUserId.ifPresent(account -> {
            AccountDTO accountDTO = accountMapper.toDTO(accountByUserId.get());
            accountEntityPersistence.setupSupportedCurrencies(accountDTO);
            userDTO.setAccount(accountDTO);
        });
        return userDTO;
    }

    @Override
    public UserDTO getByPesel(Long pesel) {
        String number = String.valueOf(pesel);
        PeselValidator.validate(number);
        Optional<UserEntity> userEntity = userEntityRepository.findByPesel(pesel);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("Not found user with this pesel {" + pesel + "}.");
        }
        return userMapper.toDTO(userEntity.get());
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        if (userDTO.getId() != null) {
            throw new ValidationException("Id must be null.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        if (userDTO.getPesel() == null) {
            throw new ValidationException("Id must be null.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByPesel(userDTO.getPesel());
        userEntityOptional.ifPresent(u -> {
                    throw new ValidationException("User with this pesel already exist.", new FieldInfo("pesel", ErrorCode.BAD_REQUEST));
                }
        );

        UserEntity userEntity = userEntityRepository.save(userMapper.fromDTO(userDTO));
        Optional<AccountEntity> accountByUserId = accountEntityRepository.findAccountByUserId(userEntity.getId());
        accountByUserId.ifPresent(account -> userEntity.setAccounts(Lists.newArrayList(accountByUserId.get())));
        return userMapper.toDTO(userEntity);
    }

    @Override
    public void delete(Long id) {
        UserDTO userDTO = getById(id);
        userEntityRepository.deleteById(userDTO.getId());
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        if (!id.equals(userDTO.getId())) {
            throw new ValidationException("Ids are not equals.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        PeselValidator.validate(userDTO.getPesel());
        UserEntity userEntity = userMapper.fromDTO(getById(id));
        userDTO.setId(userEntity.getId());
        UserEntity updatedEntity = userEntityRepository.save(userMapper.fromDTO(userDTO));
        return userMapper.toDTO(updatedEntity);
    }

}
