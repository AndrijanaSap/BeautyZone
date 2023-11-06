package com.beautyzone.beautysalonapp.rest;

import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.exception.CustomValidationException;
import com.beautyzone.beautysalonapp.rest.dto.UserDto;
import com.beautyzone.beautysalonapp.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserResource {
    private final UserServiceImpl userServiceImpl;

    public UserResource(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userServiceImpl.findAllUsers();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = userServiceImpl.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody UserDto userDto) {
        try {
            UserDto user = userServiceImpl.signUp(userDto);
            return ResponseEntity.ok(user);
        } catch (CustomValidationException ex) {
            log.error("Bad request:  " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected exception occurred. " + ex.getMessage());
        }
        return (ResponseEntity<Object>) ResponseEntity.internalServerError();
    }

//    @PutMapping("/update")
//    public ResponseEntity<User> updateUser(@RequestBody User user) {
//        User updateUser = userServiceImpl.updateUser(user);
//        return new ResponseEntity<>(updateUser, HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        userServiceImpl.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}