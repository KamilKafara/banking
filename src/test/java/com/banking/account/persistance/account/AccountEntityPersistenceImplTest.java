package com.banking.account.persistance.account;

import com.banking.account.dto.AccountDTO;
import com.banking.account.dto.UserDTO;
import com.banking.account.exception.NotFoundException;
import com.banking.account.exception.ValidationException;
import com.banking.account.exchange.rates.ExchangeApi;
import com.banking.account.exchange.rates.utils.CurrencyType;
import com.banking.account.exchange.rates.utils.ExchangeDTO;
import com.banking.account.exchange.rates.utils.ExchangeType;
import com.banking.account.persistance.user.UserEntityPersistence;
import com.banking.account.repository.AccountEntity;
import com.banking.account.repository.AccountEntityRepository;
import com.banking.account.repository.UserEntity;
import com.banking.account.repository.UserEntityRepository;
import com.banking.account.utils.mapper.AccountMapper;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountEntityPersistenceImplTest {

    @InjectMocks
    private AccountEntityPersistenceImpl accountEntityPersistence;
    @Mock
    private AccountEntityRepository accountEntityRepository;
    @Mock
    private UserEntityRepository userEntityRepository;
    @Mock
    private UserEntityPersistence userEntityPersistence;
    @Mock
    private ExchangeApi exchangeApi;
    @Mock
    private AccountMapper accountMapper;

    private AccountDTO accountDTO;
    private AccountEntity accountEntity;

    private ExchangeDTO exchangeDTO;


    @Before
    public void setUp() {
        accountDTO = initAccountDTO();
        accountEntity = initAccount();
        exchangeDTO = initExchangeDTO();
    }

    private ExchangeDTO initExchangeDTO() {
        return ExchangeDTO.builder()
                .exchangeType(ExchangeType.A)
                .currencySource(CurrencyType.PLN)
                .currencyTarget(CurrencyType.EUR)
                .targetValue(BigDecimal.TEN)
                .sourceValue(BigDecimal.TEN).build();
    }

    @Test
    public void getAllAccounts() {
        //given
        List<AccountDTO> accountDTOList = Lists.newArrayList(accountDTO);
        List<AccountEntity> accountEntities = Lists.newArrayList(accountEntity);
        //when
        when(accountEntityRepository.findAll()).thenReturn(accountEntities);
        when(accountMapper.toDTO(accountEntity)).thenReturn(accountDTO);
        List<AccountDTO> expectedResult = accountEntityPersistence.getAllAccounts();
        //then
        verify(accountEntityRepository, times(1)).findAll();
        verify(accountMapper, times(1)).toDTO(accountEntity);

        assertThat(expectedResult.size()).isEqualTo(accountDTOList.size());
        assertThat(expectedResult.get(0).getId()).isEqualTo(accountDTO.getId());
        assertThat(expectedResult.get(0).getAccountCurrencyType().name()).isEqualTo(accountDTO.getAccountCurrencyType().name());
    }

    @Test
    public void getAccountByUserIdShouldThrowNotFoundId() {
        //given
        Long id = 2L;
        //when
        Throwable throwable = catchThrowable(() -> accountEntityPersistence.getAccountById(id));
        //then
        assertThat(throwable).isInstanceOf(NotFoundException.class);
        assertThat(throwable).hasMessage("Not found account with this id {" + id + "}.");
    }

    @Test
    public void getAccountByUserPesel() {
        //given
        String pesel = "1111111111";
        //when
        when(accountEntityRepository.findAccountByUserPesel(pesel)).thenReturn(Optional.ofNullable(accountEntity));
        when(accountMapper.toDTO(accountEntity)).thenReturn(accountDTO);

        AccountDTO expectedResult = accountEntityPersistence.getAccountByUserPesel(pesel);
        //then
        verify(accountEntityRepository, times(1)).findAccountByUserPesel(pesel);
        verify(accountMapper, times(1)).toDTO(accountEntity);

        assertThat(expectedResult.getId()).isEqualTo(accountDTO.getId());
        assertThat(expectedResult.getAccountCurrencyType().name()).isEqualTo(accountDTO.getAccountCurrencyType().name());
    }

    @Test
    public void getAccountByUserPeselShouldThrowNotFoundPesel() {
        //given
        String pesel = "22222222222";
        //when
        when(accountEntityRepository.findAccountByUserPesel(pesel)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> accountEntityPersistence.getAccountByUserPesel(pesel));
        verify(accountEntityRepository, times(1)).findAccountByUserPesel(pesel);
        //then
        assertThat(throwable).isInstanceOf(NotFoundException.class);
        assertThat(throwable).hasMessage("Not found account assigned to user with this pesel {" + pesel + "}.");
    }

    @Test
    public void saveShouldFailed() {
        //given
        AccountDTO accountToSave = initAccountDTO();
        accountToSave.setId(null);
        //when
        Throwable throwable = catchThrowable(() -> accountEntityPersistence.save(accountToSave));
        //then
        assertThat(throwable).isInstanceOf(ValidationException.class);
        assertThat(throwable).hasMessage("Cannot create account without user data.");
    }

    @Test
    public void delete() throws IOException {
        //given
        Long id = 1L;
        exchangeDTO.setTargetValue(BigDecimal.ZERO);
        exchangeDTO.setSourceValue(BigDecimal.ZERO);
        //when
        when(accountEntityRepository.findById(1L)).thenReturn(Optional.ofNullable(accountEntity));
        when(accountMapper.toDTO(accountEntity)).thenReturn(accountDTO);
        when(exchangeApi.exchangeRate(
                ExchangeType.A,
                accountDTO.getAccountCurrencyType(),
                CurrencyType.PLN,
                accountDTO.getCurrentBalance()))
                .thenReturn(exchangeDTO);
        when(exchangeApi.exchangeRate(
                ExchangeType.A,
                accountDTO.getAccountCurrencyType(),
                CurrencyType.HUF,
                accountDTO.getCurrentBalance()))
                .thenReturn(exchangeDTO);
        when(exchangeApi.exchangeRate(
                ExchangeType.A,
                accountDTO.getAccountCurrencyType(),
                CurrencyType.EUR,
                accountDTO.getCurrentBalance()))
                .thenReturn(exchangeDTO);
        when(exchangeApi.exchangeRate(
                ExchangeType.A,
                accountDTO.getAccountCurrencyType(),
                CurrencyType.USD,
                accountDTO.getCurrentBalance()))
                .thenReturn(exchangeDTO);
        when(accountMapper.fromDTO(accountDTO)).thenReturn(accountEntity);
        accountEntityPersistence.delete(id);
        //then
        verify(accountEntityRepository, times(1)).delete(accountEntity);
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

    private UserDTO initUserEntityDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("firstName");
        userDTO.setSecondName("secondName");
        userDTO.setPesel("1111111111");
        return userDTO;
    }

    private UserEntity initUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("firstName");
        userEntity.setSecondName("secondName");
        userEntity.setPesel("1111111111");
        return userEntity;
    }
}
