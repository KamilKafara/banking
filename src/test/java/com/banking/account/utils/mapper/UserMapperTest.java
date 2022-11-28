package com.banking.account.utils.mapper;

import com.banking.account.dto.UserDTO;
import com.banking.account.repository.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void fromDTO() {
        //given
        UserEntity userEntity = initUserEntity();
        //when
        UserEntity expectedResult = userMapper.fromDTO(initUserEntityDTO());
        //then
        assertThat(expectedResult.getId()).isEqualTo(userEntity.getId());
        assertThat(expectedResult.getPesel()).isEqualTo(userEntity.getPesel());
        assertThat(expectedResult.getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(expectedResult.getSecondName()).isEqualTo(userEntity.getSecondName());
    }

    @Test
    public void toDTO() {
        //given
        UserDTO userDTO = initUserEntityDTO();
        //when
        UserDTO expectedResult = userMapper.toDTO(initUserEntity());

        //then
        assertThat(expectedResult.getId()).isEqualTo(userDTO.getId());
        assertThat(expectedResult.getPesel()).isEqualTo(userDTO.getPesel());
        assertThat(expectedResult.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(expectedResult.getSecondName()).isEqualTo(userDTO.getSecondName());
    }

    private UserDTO initUserEntityDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("firstName");
        userDTO.setSecondName("secondName");
        userDTO.setPesel("1111111111");
        return userDTO;
    }

    private UserEntity initUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("firstName");
        userEntity.setSecondName("secondName");
        userEntity.setPesel("1111111111");
        return userEntity;
    }
}
