package com.hackmajoris.entitytodto;

import com.hackmajoris.entitytodto.model.User;
import com.hackmajoris.entitytodto.model.dtos.DTOEntity;
import com.hackmajoris.entitytodto.model.dtos.UserCreateDTO;
import com.hackmajoris.entitytodto.utils.DtoUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserDtoUnitTest {

    @Test
    public void userEntityToUserDto() {

        // Given
        User user = new User();
        user.setId(1);
        user.setEmail("user1@example.com");
        user.setName("user1");
        user.setPassword("user1Password");

        // When
        UserCreateDTO userCreateDTO =  (UserCreateDTO) new DtoUtils().convertToDto(user, new UserCreateDTO());

        // Then
        assertEquals(user.getEmail(), userCreateDTO.getEmail());
        assertEquals(user.getName(), userCreateDTO.getName());
        assertEquals(user.getPassword(), userCreateDTO.getPassword());
    }

    @Test
    public void userDtoToUserEntity() {

        // Given
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("user1@example.com");
        userCreateDTO.setName("user1");
        userCreateDTO.setPassword("user1Password");

        // When
        User user =  (User) new DtoUtils().convertToEntity(new User(), userCreateDTO);

        // Then
        assertEquals(user.getEmail(), userCreateDTO.getEmail());
        assertEquals(user.getName(), userCreateDTO.getName());
        assertEquals(user.getPassword(), userCreateDTO.getPassword());
    }

}
