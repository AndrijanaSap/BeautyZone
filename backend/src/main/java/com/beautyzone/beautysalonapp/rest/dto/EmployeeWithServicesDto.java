package com.beautyzone.beautysalonapp.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeWithServicesDto extends UserDto{
 List<ServiceDto> services;
}
