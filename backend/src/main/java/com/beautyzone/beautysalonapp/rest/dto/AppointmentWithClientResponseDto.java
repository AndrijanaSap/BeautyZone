package com.beautyzone.beautysalonapp.rest.dto;

import com.beautyzone.beautysalonapp.constants.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentWithClientResponseDto {
    private Integer id;
    private String paymentMethod;
    private ServiceDto service;
    private UserDto client;
    private String name;
    private String phoneNumber;
    private String email;
    private String note;
    private AppointmentStatus appointmentStatus;
    private LocalDateTime appointmentDateTime;
}
