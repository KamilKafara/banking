package com.banking.account.persistance.user;

import com.banking.account.dto.UserDTO;

import java.util.List;

public interface UserEntityPersistence {
    List<UserDTO> getAllUser();

    UserDTO getById(Long id);

    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO, Long id);

    void delete(Long id);

    UserDTO getByPesel(Long pesel);
}
