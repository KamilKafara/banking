package com.banking.account.mapper;

import com.banking.account.dto.UserDTO;
import com.banking.account.repository.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public
class UserMapper {
    public UserEntity fromDTO(UserDTO userDTO) {
        return new ModelMapper().map(userDTO, UserEntity.class);
    }

    public UserDTO toDTO(UserEntity userEntity) {
        return new ModelMapper().map(userEntity, UserDTO.class);
    }
}
