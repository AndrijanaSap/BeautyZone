package com.beautyzone.beautysalonapp.rest.dto;
//import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.domain.Service;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class AppointmentRequestDto {
    private List<Integer> timeSlotIds;

    // Appointment data
    private String paymentMethod;
    private Integer serviceId;
    private Integer employeeId;
    private String name;
    private String phoneNumber;
    private String email;
    private String note;
}
