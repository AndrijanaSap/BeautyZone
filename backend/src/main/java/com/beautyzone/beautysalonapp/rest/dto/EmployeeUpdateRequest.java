package com.beautyzone.beautysalonapp.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateRequest extends EmployeeRegisterRequest {
    private Integer id;
}