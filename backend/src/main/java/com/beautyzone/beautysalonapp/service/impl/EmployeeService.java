package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.constants.Role;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.repository.EmployeeRepository;
import com.beautyzone.beautysalonapp.repository.ServiceRepository;
import com.beautyzone.beautysalonapp.rest.dto.*;
//import com.beautyzone.beautysalonapp.rest.mapper.EmployeeMapper;
import com.beautyzone.beautysalonapp.rest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    //    private final EmployeeMapper employeeMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getEmployeesByServiceId(Integer serviceId) {
        return userMapper.usersToUserDtos(employeeRepository.findEmployeesByServiceId(serviceId));
    }

    public List<EmployeeWithServicesDto> getAllEmployees() {
        return userMapper.usersToEmployeeWithServicesDtos(employeeRepository.findAllByRole(Role.EMPLOYEE));
    }

    public void addEmployee(EmployeeRegisterRequest request) {
        ;
        var user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Role.EMPLOYEE)
                .services(serviceRepository.findAllById(request.getServices()))
                .build();
        employeeRepository.save(user);
    }

    public boolean deleteEmployeeById(Integer id) {
        try{
        employeeRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return !employeeRepository.existsById(id);
    }

    public EmployeeWithServicesDto getEmployeeById(Integer userId) {
        User user = employeeRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.userToEmployeeWithServicesDto(user);
    }

    public void updateEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
        User user = employeeRepository.findById(employeeUpdateRequest.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setName(employeeUpdateRequest.getName());
        user.setSurname(employeeUpdateRequest.getSurname());
        user.setPhone(employeeUpdateRequest.getPhone());
        user.setEmail(employeeUpdateRequest.getEmail());
        user.setServices(serviceRepository.findAllById(employeeUpdateRequest.getServices()));
        employeeRepository.save(user);
    }
}