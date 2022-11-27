package com.banking.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

//@EqualsAndHashCode(exclude = "accounts")
public class UserDTO {
    private Long id;

    private String firstName;

    private String secondName;

    private Long pesel;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AccountDTO account;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String secondName, Long pesel, AccountDTO account) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.pesel = pesel;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }
}
