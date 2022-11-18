package com.prime.banking.app.persistance;

import com.google.common.primitives.Longs;
import com.prime.banking.app.dto.UserDTO;
import com.prime.banking.app.exception.ValidationException;
import com.prime.banking.app.exception.handler.ErrorCode;
import com.prime.banking.app.exception.handler.FieldInfo;
import com.prime.banking.app.mapper.UserMapper;
import com.prime.banking.app.exception.NotFoundException;
import com.prime.banking.app.repository.UserEntity;
import com.prime.banking.app.repository.UserEntityRepository;
import com.prime.banking.app.utils.validate.PeselValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.pl.PESELValidator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        if (userDTO.getId() != null) {
            throw new ValidationException("Id must be null.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        UserEntity userEntity = userEntityRepository.save(userMapper.fromDTO(userDTO));
        return userMapper.toDTO(userEntity);
    }

    @Override
    public void delete(Long id) {
        UserDTO userDTO = getById(id);
        userEntityRepository.deleteById(userDTO.getId());
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        if (!id.equals(userDTO.getId())) {
            throw new ValidationException("Ids are not equals.", new FieldInfo("id", ErrorCode.BAD_REQUEST));
        }
        UserEntity userEntity = userMapper.fromDTO(userDTO);
        userDTO.setId(userEntity.getId());
        UserEntity updatedEntity = userEntityRepository.save(userMapper.fromDTO(userDTO));
        return userMapper.toDTO(updatedEntity);
    }

    @Override
    public Optional<UserDTO> getByPesel(Long pesel) {
        String number = String.valueOf(pesel);
//        new PESELValidator().isCheckDigitValid();
        Optional<UserEntity> userEntity = userEntityRepository.findByPesel(pesel);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("Not found post with this id {" + pesel + "}.");
        }
        return Optional.ofNullable(userMapper.toDTO(userEntity.get()));
    }

}
