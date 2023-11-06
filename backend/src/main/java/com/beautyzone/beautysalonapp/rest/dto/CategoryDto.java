package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private Integer id;
    private String name;
    private List<ServiceDto> services;
}
