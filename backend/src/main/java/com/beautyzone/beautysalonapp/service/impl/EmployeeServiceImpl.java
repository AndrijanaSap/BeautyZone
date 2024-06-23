package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.constants.Role;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.repository.EmployeeRepository;
import com.beautyzone.beautysalonapp.repository.ServiceRepository;
import com.beautyzone.beautysalonapp.rest.dto.*;
//import com.beautyzone.beautysalonapp.rest.mapper.EmployeeMapper;
import com.beautyzone.beautysalonapp.rest.mapper.UserMapper;
import com.beautyzone.beautysalonapp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getEmployeesByServiceId(Integer serviceId) {
        return userMapper.usersToUserDtos(employeeRepository.findEmployeesByServiceId(serviceId));
    }

    @Override
    public List<EmployeeWithServicesDto> getAllEmployees() {
        return userMapper.usersToEmployeeWithServicesDtos(employeeRepository.findAllByRole(Role.EMPLOYEE));
    }
    @Override
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
    @Override
    public boolean deleteEmployeeById(Integer id) {
        try{
        employeeRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return !employeeRepository.existsById(id);
    }
    @Override
    public EmployeeWithServicesDto getEmployeeById(Integer userId) {
        User user = employeeRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.userToEmployeeWithServicesDto(user);
    }
    @Override
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