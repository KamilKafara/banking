package com.banking.account.controller;

import com.banking.account.dto.UserDTO;
import com.banking.account.persistance.user.UserEntityPersistence;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserEntityPersistence userEntityPersistence;

    UserController(UserEntityPersistence userEntityPersistence) {
        this.userEntityPersistence = userEntityPersistence;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userEntityPersistence.getAllUser();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        return userEntityPersistence.getById(id);
    }

    @GetMapping("/pesel/{pesel}")
    public UserDTO getUserByPesel(@PathVariable("pesel") Long pesel) {
        return userEntityPersistence.getByPesel(pesel);
    }

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userEntityPersistence.save(userDTO);
    }

    @PostMapping("/update/{id}")
    public UserDTO updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Long id) {
        return userEntityPersistence.update(userDTO, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userEntityPersistence.delete(id);
    }

}
