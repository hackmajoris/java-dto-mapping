package com.hackmajoris.entitytodto.model.dtos;

public class UserCreateDTO implements DTOEntity {

    private String name;
    private String email;
    private String password;

    public UserCreateDTO(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
