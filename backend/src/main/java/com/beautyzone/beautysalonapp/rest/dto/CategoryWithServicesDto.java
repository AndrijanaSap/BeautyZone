package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryWithServicesDto {
    private Integer id;
    private String name;
    private String jobPosition;
    private List<ServiceDto> services;
}
