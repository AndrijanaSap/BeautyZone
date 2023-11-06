package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.exception.CustomValidationException;
import com.beautyzone.beautysalonapp.exception.UserNotFoundException;
import com.beautyzone.beautysalonapp.repository.UserRepository;
import com.beautyzone.beautysalonapp.rest.dto.UserDto;
import com.beautyzone.beautysalonapp.rest.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.beautyzone.beautysalonapp.constants.ErrorConstants.*;

@Service
@Slf4j
public class UserServiceImpl {

    private final UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public List<User> findAllUsers() {
//        return UserRepository.findAll();
//    }
//
//    public User updateUser(User User) {
//        return UserRepository.save(User);
//    }

    public User findUserById(Integer id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteUser(Integer id){
        userRepository.deleteUserById(id);
    }
    public UserDto signUp(UserDto userDto) throws CustomValidationException{
        log.info("Entered signUp with data: {}", userDto);
        validateSignUpMap(userDto);

        if(userRepository.findUserByEmail(userDto.getEmail()).isPresent())
         throw new CustomValidationException("User with email " + userDto.getEmail() + " already exists.");

        User user = userRepository.save(userMapper.userDtoToUser(userDto));
        log.info("New user saved.");
        return userMapper.userToUserDto(user);
    }

    private void validateSignUpMap(UserDto userDto) throws CustomValidationException{
        //Mandatory fields
        if(userDto.getName() == null || userDto.getName().isEmpty()){
            throw new CustomValidationException(REQUIRED_NAME);
        }
        if(userDto.getSurname() == null || userDto.getSurname().isEmpty()){
            throw new CustomValidationException(REQUIRED_SURNAME);
        }
        if(userDto.getEmail() == null || userDto.getEmail().isEmpty()){
            throw new CustomValidationException(REQUIRED_EMAIL);
        }
        if(userDto.getPhone() == null || userDto.getPhone().isEmpty()){
            throw new CustomValidationException(REQUIRED_PHONE);
        }
        if(userDto.getPassword() == null || userDto.getPassword().isEmpty()){
            throw new CustomValidationException(REQUIRED_PASSWORD);
        }
    }
}