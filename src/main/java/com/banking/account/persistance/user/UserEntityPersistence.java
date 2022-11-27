package com.banking.account.persistance.user;

import com.banking.account.dto.UserDTO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserEntityPersistence {
    List<UserDTO> getAllUser();

    @Query("SELECT * FROM UserEntity u JOIN AccountEntity a ON u.id = a.user_id WHERE u.id = ?1 ")
    UserDTO getById(Long id);

    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO, Long id);

    void delete(Long id);

    UserDTO getByPesel(Long pesel);
}
