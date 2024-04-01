package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDto {
    private Integer id;
    private String name;
    private String description;
    private String imgPath;
    private Integer durationInMinutes;
    private Double price;
}
