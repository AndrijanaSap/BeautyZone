package com.beautyzone.beautysalonapp.service;

import com.beautyzone.beautysalonapp.rest.dto.ChangePasswordRequest;
import com.beautyzone.beautysalonapp.rest.dto.UserDto;

import java.security.Principal;
import java.util.List;

public interface UserService {

    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    UserDto findById(Integer id);

    List<UserDto> findAllClients();

    UserDto update(UserDto userDto);

    boolean deleteClientById(Integer id);
}
