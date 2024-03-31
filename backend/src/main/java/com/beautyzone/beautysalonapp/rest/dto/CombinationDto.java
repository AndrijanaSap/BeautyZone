package com.beautyzone.beautysalonapp.rest.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class CombinationDto {
    private LocalDateTime startDateTime;
    private Integer employeeId;
    private List<Integer> timeSlotIds;
}
