package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.rest.dto.ChangePasswordRequest;
import com.beautyzone.beautysalonapp.rest.dto.UserDto;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.repository.UserRepository;
import com.beautyzone.beautysalonapp.rest.dto.ServiceWithCategoryDto;
import com.beautyzone.beautysalonapp.rest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserMapper userMapper;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public UserDto findById(Integer id) {
        User user = repository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return userMapper.userToUserDto(user);
    }

    public UserDto update(UserDto userDto) {
        // Get from db
        User userDb = repository.findUserById(userDto.getId()).orElseThrow(() -> new NoSuchElementException("User not found with id: " + userDto.getId()));
        userDb.setName(userDto.getName());
        userDb.setSurname(userDto.getSurname());
        userDb.setPhone(userDto.getPhone());
        repository.save(userDb);
        return userMapper.userToUserDto(userDb);
    }
}