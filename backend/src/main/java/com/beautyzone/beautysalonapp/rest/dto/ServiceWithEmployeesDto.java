package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServiceWithEmployeesDto extends ServiceDto{
    private List<EmployeeDto> employees;
    private CategoryDto category;
}
