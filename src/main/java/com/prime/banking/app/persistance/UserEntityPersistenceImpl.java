package com.prime.banking.app.persistance;

import com.prime.banking.app.dto.UserDTO;
import com.prime.banking.app.mapper.UserMapper;
import com.prime.banking.app.exception.NotFoundException;
import com.prime.banking.app.repository.UserEntity;
import com.prime.banking.app.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class UserEntityPersistenceImpl implements UserEntityPersistence {
    private final UserEntityRepository userEntityRepository;
    private final UserMapper userMapper;

    UserEntityPersistenceImpl(UserEntityRepository userEntityRepository, UserMapper userMapper) {
        this.userEntityRepository = userEntityRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUser() {
        return userEntityRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(Long id) {
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("Not found post with this id {" + id + "}.");
        }
        return userMapper.toDTO(userEntity.get());
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        return null;
    }

    @Override
    public Optional<UserDTO> getByName(String name) {
        return Optional.empty();
    }
}
