package com.beautyzone.beautysalonapp.rest.dto;

import com.beautyzone.beautysalonapp.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegisterRequest extends RegisterRequest {
    private List<Integer> services;
}
