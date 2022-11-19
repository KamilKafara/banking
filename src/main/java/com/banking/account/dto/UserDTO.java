package com.banking.account.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;

    private String firstName;

    private String secondName;

    private Long pesel;

    private AccountDTO account;
}
