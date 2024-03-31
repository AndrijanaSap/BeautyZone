package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Data;

@Data
public class AppointmentCreationResponseDto {
    private Integer id;
    private String paymentMethod;
    private Boolean success;
}
