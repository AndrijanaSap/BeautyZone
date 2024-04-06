package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryUpdateRequestDto extends CategoryDto{
    private List<Integer> services;
}
