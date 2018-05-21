package com.hackmajoris.entitytodto.service;

import com.hackmajoris.entitytodto.model.User;
import com.hackmajoris.entitytodto.model.dtos.DTOEntity;
import com.hackmajoris.entitytodto.model.dtos.UserCreateDTO;
import com.hackmajoris.entitytodto.model.dtos.UserReadDTO;
import com.hackmajoris.entitytodto.model.dtos.UserUpdateDTO;
import com.hackmajoris.entitytodto.utils.DtoUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public DTOEntity createUser(DTOEntity userCreateDto){
        // Let's convert DTO to Entity and then again - Entity to DTO
        User user = (User) new DtoUtils().convertToEntity(new User(), userCreateDto);

        return new DtoUtils().convertToDto(user, new UserCreateDTO());
    }

    public DTOEntity readUser(){
        User user = new User();
        user.setId(1);
        user.setName("User number 1");
        user.setEmail("Email number 1");
        user.setPassword("Password number 1");

        return new DtoUtils().convertToDto(user, new UserReadDTO());
    }

    public DTOEntity updateUser(DTOEntity userUpdateDTO) {
        // Let's convert DTO to Entity and then again - Entity to DTO
        User user = (User) new DtoUtils().convertToEntity(new User(), userUpdateDTO);

        return new DtoUtils().convertToDto(user, new UserUpdateDTO());
    }
}
