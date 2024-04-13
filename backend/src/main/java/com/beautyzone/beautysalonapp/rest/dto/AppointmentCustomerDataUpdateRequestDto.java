package com.beautyzone.beautysalonapp.rest.dto;

import lombok.Data;

@Data
public class AppointmentCustomerDataUpdateRequestDto {
    private Integer id;
    private String name;
    private String note;
    private String email;
    private String phone;
}
