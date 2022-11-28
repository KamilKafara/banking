package com.banking.account.utils.mapper;

import com.banking.account.dto.AccountDTO;
import com.banking.account.dto.UserDTO;
import com.banking.account.exchange.rates.utils.CurrencyType;
import com.banking.account.repository.AccountEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AccountMapperTest {

    @InjectMocks
    private AccountMapper accountMapper;

    @Test
    public void fromDTO() {
        //given
        AccountDTO accountDTO = initAccountDTO();
        //when
        AccountEntity expectedResult = accountMapper.fromDTO(accountDTO);
        //then
        assertThat(expectedResult.getId()).isEqualTo(accountDTO.getId());
        assertThat(expectedResult.getCurrentBalance()).isEqualTo(accountDTO.getCurrentBalance());
        assertThat(expectedResult.getCurrencyType().name()).isEqualTo(accountDTO.getAccountCurrencyType().name());
    }

    @Test
    public void toDTO() {
        //given
        AccountDTO accountDTO = initAccountDTO();
        AccountEntity account = initAccount();
        //when
        AccountDTO expectedResult = accountMapper.toDTO(account);
        //then
        assertThat(expectedResult.getId()).isEqualTo(accountDTO.getId());
        assertThat(expectedResult.getCurrentBalance()).isEqualTo(accountDTO.getCurrentBalance());
        assertThat(expectedResult.getUser()).isEqualTo(accountDTO.getUser());
        assertThat(expectedResult.getAccountCurrencyType().name()).isEqualTo(accountDTO.getAccountCurrencyType().name());

    }

    private AccountDTO initAccountDTO() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountCurrencyType(CurrencyType.PLN);
        accountDTO.setCurrentBalance(BigDecimal.TEN);
        return accountDTO;
    }

    private AccountEntity initAccount() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setCurrencyType(CurrencyType.PLN);
        accountEntity.setCurrentBalance(BigDecimal.TEN);
        return accountEntity;
    }

}
