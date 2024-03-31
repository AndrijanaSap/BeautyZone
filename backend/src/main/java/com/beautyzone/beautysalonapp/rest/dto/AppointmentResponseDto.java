package com.beautyzone.beautysalonapp.rest.dto;
import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.domain.Service;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class AppointmentDto {
    private UUID id;
    private String name;
    private Service service;
    private Employee employee;
    private LocalDateTime creationTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
