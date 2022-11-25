package com.banking.account.utils.mapper;

import com.banking.account.dto.UserDTO;
import com.banking.account.repository.UserEntity;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private final AccountMapper accountMapper;

    @Autowired
    public UserMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public UserEntity fromDTO(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setPesel(userDTO.getPesel());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setSecondName(userDTO.getSecondName());
        return userEntity;
    }

    public UserDTO toDTO(UserEntity userEntity) {
        UserDTO dto = new UserDTO();
        dto.setId(userEntity.getId());
        dto.setPesel(userEntity.getPesel());
        dto.setFirstName(userEntity.getFirstName());
        dto.setSecondName(userEntity.getSecondName());
        return dto;
    }
}
