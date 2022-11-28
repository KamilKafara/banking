package com.banking.account.utils.mapper;

import com.banking.account.dto.AccountDTO;
import com.banking.account.dto.UserDTO;
import com.banking.account.exchange.rates.utils.CurrencyType;
import com.banking.account.repository.AccountEntity;
import com.banking.account.repository.UserEntity;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AccountMapperTest {

    @InjectMocks
    private AccountMapper accountMapper;

    @Mock
    private UserMapper userMapper;

    AccountEntity account;
    AccountDTO accountDTO;
    UserEntity userEntity;
    UserDTO userDTO;

    @BeforeEach
    void setUp() {
       account = initAccount();
       accountDTO = initAccountDTO();
       userEntity = initUserEntity();
       userDTO = initUserEntityDTO();
    }

    private UserDTO initUserEntityDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setSecondName("firstName");
        userDTO.setFirstName("firstName");
        userDTO.setPesel("11111111111");
        userDTO.setAccount(initAccountDTO());
        return userDTO;
    }

    private UserEntity initUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setSecondName("secondName");
        userEntity.setFirstName("firstName");
        userEntity.setAccounts(Lists.newArrayList(initAccount()));
        userEntity.setPesel("111111111");
        return userEntity;
    }

    private AccountDTO initAccountDTO() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountCurrencyType(CurrencyType.PLN);
        accountDTO.setUser(initUserEntityDTO());
        accountDTO.setCurrentBalance(BigDecimal.TEN);
        return accountDTO;
    }

    private AccountEntity initAccount() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setUser(initUserEntity());
        accountEntity.setCurrencyType(CurrencyType.PLN);
        accountEntity.setCurrentBalance(BigDecimal.TEN);
        return accountEntity;
    }

    @Test
    public void fromDTO() {
        //given

        //when

        //then
    }

    @Test
    public void toDTO() {
        //given

        //when

        //then
    }
}
