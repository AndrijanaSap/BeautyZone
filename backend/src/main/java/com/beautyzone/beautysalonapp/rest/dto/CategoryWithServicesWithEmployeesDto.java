package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryWithServicesWithEmployeesDto {
    private Integer id;
    private String name;
    private List<ServiceWithEmployeesDto> services;
}
