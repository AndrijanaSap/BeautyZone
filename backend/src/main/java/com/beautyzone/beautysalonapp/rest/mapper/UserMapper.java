package com.beautyzone.beautysalonapp.rest.mapper;

import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.rest.dto.EmployeeWithServicesDto;
import com.beautyzone.beautysalonapp.rest.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);
    @Named("userToUserDto")
    UserDto userToUserDto(User user);
    @Named("usersToUserDtos")
    List<UserDto> usersToUserDtos(List<User> users);
    EmployeeWithServicesDto userToEmployeeWithServicesDto(User user);
    List<EmployeeWithServicesDto> usersToEmployeeWithServicesDtos(List<User> users);
}
