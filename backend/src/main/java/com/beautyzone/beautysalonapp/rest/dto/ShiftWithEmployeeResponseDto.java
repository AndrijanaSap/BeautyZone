package com.beautyzone.beautysalonapp.rest.dto;

import com.beautyzone.beautysalonapp.constants.ShiftType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShiftWithEmployeeResponseDto {
    private Integer id;
    private EmployeeDto employee;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private ShiftType shiftType;
}
