package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
public class EmployeeDto {
    private Integer id;
    private String name;
    private String surname;
}
