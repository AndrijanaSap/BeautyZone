package com.beautyzone.beautysalonapp.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterRequestDto {

    private String username;

    private String password;

    private String repeatedPassword;

    private String name;

    private String surname;
}