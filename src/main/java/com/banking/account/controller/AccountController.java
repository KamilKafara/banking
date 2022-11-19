package com.banking.account.controller;

import com.banking.account.dto.AccountDTO;
import com.banking.account.persistance.account.AccountEntityPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController implements AccountEntityPersistence {

    private final AccountEntityPersistence accountEntityPersistence;

    @Autowired
    AccountController(AccountEntityPersistence accountEntityPersistence) {
        this.accountEntityPersistence = accountEntityPersistence;
    }

    @GetMapping
    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountEntityPersistence.getAllAccounts();
    }

    @GetMapping("/{id}")
    @Override
    public AccountDTO getAccountById(@PathVariable("id") Long id) {
        return accountEntityPersistence.getAccountById(id);
    }

    @GetMapping("/user/{id}")
    @Override
    public AccountDTO getAccountByUserId(@PathVariable("id") Long id) {
        return accountEntityPersistence.getAccountByUserId(id);
    }

    @GetMapping("/user/pesel/{pesel}")
    @Override
    public AccountDTO getAccountByUserPesel(@PathVariable("pesel") Long pesel) {
        return accountEntityPersistence.getAccountByUserPesel(pesel);
    }

    @PostMapping
    @Override
    public AccountDTO save(@RequestBody AccountDTO accountDTO) {
        return accountEntityPersistence.save(accountDTO);
    }

    @PatchMapping("/{id}")
    @Override
    public AccountDTO update(@RequestBody AccountDTO accountDTO, @PathVariable("id") Long id) {
        return accountEntityPersistence.update(accountDTO, id);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable("id") Long id) {
        accountEntityPersistence.delete(id);
    }
}
