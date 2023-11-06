package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Data;

@Data
public class ServiceDto {
    private Integer id;
    private String name;
    private Integer durationInMinutes;
    private Double price;
}
