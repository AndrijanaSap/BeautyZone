package com.beautyzone.beautysalonapp.service;

import com.beautyzone.beautysalonapp.rest.dto.EmployeeRegisterRequest;
import com.beautyzone.beautysalonapp.rest.dto.EmployeeUpdateRequest;
import com.beautyzone.beautysalonapp.rest.dto.EmployeeWithServicesDto;
import com.beautyzone.beautysalonapp.rest.dto.UserDto;

import java.util.List;

public interface EmployeeService {

    /**
     * Retrieves employees by service ID.
     *
     * @param serviceId The ID of the service for which employees are to be retrieved.
     * @return List of UserDto objects representing employees.
     */
    List<UserDto> getEmployeesByServiceId(Integer serviceId);

    /**
     * Retrieves all employees.
     *
     * @return List of EmployeeWithServicesDto objects representing all employees.
     */
    List<EmployeeWithServicesDto> getAllEmployees();

    /**
     * Adds a new employee.
     *
     * @param request The EmployeeRegisterRequest containing employee details.
     */
    void addEmployee(EmployeeRegisterRequest request);

    /**
     * Deletes an employee by ID.
     *
     * @param id The ID of the employee to delete.
     * @return true if the employee was successfully deleted, false otherwise.
     */
    boolean deleteEmployeeById(Integer id);

    /**
     * Retrieves an employee by ID.
     *
     * @param userId The ID of the employee to retrieve.
     * @return EmployeeWithServicesDto representing the employee.
     */
    EmployeeWithServicesDto getEmployeeById(Integer userId);

    /**
     * Updates an existing employee.
     *
     * @param employeeUpdateRequest The EmployeeUpdateRequest containing updated employee details.
     */
    void updateEmployee(EmployeeUpdateRequest employeeUpdateRequest);

}