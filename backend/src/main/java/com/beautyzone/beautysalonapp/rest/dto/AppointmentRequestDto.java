package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Data;
import java.util.List;

@Data
public class AppointmentRequestDto {
    private List<Integer> timeSlotIds;

    // Appointment data
    private String id;
    private String paymentMethod;
    private Integer serviceId;
    private Integer employeeId;
    private String name;
    private String phoneNumber;
    private String email;
    private String note;
}
