package com.prime.banking.app.persistance;

import com.prime.banking.app.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserEntityPersistence {
    List<UserDTO> getAllUser();

    UserDTO getById(Long id);

    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO, Long id);

    void delete(Long id);

    Optional<UserDTO> getByPesel(Long pesel);
}
