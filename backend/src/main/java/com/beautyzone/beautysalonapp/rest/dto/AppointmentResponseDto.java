package com.beautyzone.beautysalonapp.rest.dto;
import com.beautyzone.beautysalonapp.constants.AppointmentStatus;
//import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.domain.Service;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDto {
    private Integer id;
    private String paymentMethod;
    private ServiceDto service;
    private EmployeeDto employee;
    private String name;
    private String phoneNumber;
    private String email;
    private String note;
    private AppointmentStatus appointmentStatus;
    private LocalDateTime appointmentDateTime;
}
