package com.banking.account.utils.mapper;

import com.banking.account.dto.UserDTO;
import com.banking.account.repository.UserEntity;
import com.google.common.collect.Lists;

public class UserMapper {
    public static UserEntity fromDTO(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setPesel(userDTO.getPesel());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setSecondName(userDTO.getSecondName());
        if (userDTO.getAccount() != null) {
            userEntity.setAccounts(Lists.newArrayList(AccountMapper.fromDTO(userDTO.getAccount())));
        }
        return userEntity;
    }

    public static UserDTO toDTO(UserEntity userEntity) {
        UserDTO dto = new UserDTO();
        dto.setId(userEntity.getId());
        dto.setPesel(userEntity.getPesel());
        dto.setFirstName(userEntity.getFirstName());
        dto.setSecondName(userEntity.getSecondName());
        if (userEntity.getAccounts() != null) {
            userEntity.getAccounts().stream().findFirst().ifPresent(account ->
                    dto.setAccount(AccountMapper.toDTO(account))
            );
        }
        return dto;

    }
}
