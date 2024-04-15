package com.beautyzone.beautysalonapp.rest.dto;

import com.beautyzone.beautysalonapp.constants.HolidayType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HolidayWithEmployeeResponseDto {
    private Integer id;
    private EmployeeDto employee;
    private String name;
    private HolidayType holidayType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
