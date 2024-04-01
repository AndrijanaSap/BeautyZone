package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServiceUpdateRequestDto extends ServiceDto{
    private Integer categoryId;
    private List<Integer> employees;
}
