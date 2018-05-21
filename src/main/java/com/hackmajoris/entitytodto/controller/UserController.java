package com.hackmajoris.entitytodto.controller;

import com.hackmajoris.entitytodto.model.dtos.DTOEntity;
import com.hackmajoris.entitytodto.model.dtos.UserCreateDTO;
import com.hackmajoris.entitytodto.model.dtos.UserUpdateDTO;
import com.hackmajoris.entitytodto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class UserController {

    private UserService updateService;

    @Autowired
    public UserController(UserService updateService){
        this.updateService = updateService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public DTOEntity createPost(@RequestBody UserCreateDTO userCreateDTO) {
        return updateService.createUser(userCreateDTO);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public DTOEntity readUser() {
        return updateService.readUser();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public DTOEntity updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {

        return updateService.updateUser(userUpdateDTO);
    }

}
