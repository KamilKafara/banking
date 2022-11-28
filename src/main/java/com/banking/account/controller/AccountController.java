package com.banking.account.controller;

import com.banking.account.dto.AccountDTO;
import com.banking.account.persistance.account.AccountEntityPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController  {

    private final AccountEntityPersistence accountEntityPersistence;

    @Autowired
    AccountController(AccountEntityPersistence accountEntityPersistence) {
        this.accountEntityPersistence = accountEntityPersistence;
    }

    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        return accountEntityPersistence.getAllAccounts();
    }

    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable("id") Long id) {
        return accountEntityPersistence.getAccountById(id);
    }

    @GetMapping("/user/{id}")
    public AccountDTO getAccountByUserId(@PathVariable("id") Long id) {
        return accountEntityPersistence.getAccountByUserId(id);
    }

    @GetMapping("/user/pesel/{pesel}")
    public AccountDTO getAccountByUserPesel(@PathVariable("pesel") String pesel) {
        return accountEntityPersistence.getAccountByUserPesel(pesel);
    }

    @PostMapping
    public AccountDTO save(@RequestBody AccountDTO accountDTO) {
        return accountEntityPersistence.save(accountDTO);
    }

    @PatchMapping("/{id}")
    public AccountDTO update(@RequestBody AccountDTO accountDTO, @PathVariable("id") Long id) {
        return accountEntityPersistence.update(accountDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        accountEntityPersistence.delete(id);
    }
}
