package com.beautyzone.beautysalonapp.rest.dto;
import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.domain.Service;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CheckAvailabilityRequestDto {
    private UUID id;
    private String name;
    private Service service;
    private Employee employee;
    private LocalDateTime creationDateTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
