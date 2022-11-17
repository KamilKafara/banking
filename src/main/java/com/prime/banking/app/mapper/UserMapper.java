package com.prime.banking.app.mapper;

import com.prime.banking.app.dto.UserDTO;
import com.prime.banking.app.repository.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public
class UserMapper {
    public UserEntity fromDTO(UserDTO UserDTO) {
        return new ModelMapper().map(UserDTO, UserEntity.class);
    }

    public UserDTO toDTO(UserEntity UserEntity) {
        return new ModelMapper().map(UserEntity, UserDTO.class);
    }
}
