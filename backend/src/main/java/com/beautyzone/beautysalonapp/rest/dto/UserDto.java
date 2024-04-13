package com.beautyzone.beautysalonapp.rest.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String role;
    private String ipAddress;

    public UserDto(){
    }
}
