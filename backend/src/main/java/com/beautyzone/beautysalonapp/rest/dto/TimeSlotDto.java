package com.beautyzone.beautysalonapp.rest.dto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class TimeSlotDto {
    private LocalDateTime dateTime; //startDate
    List<CombinationDto> combinationDtos;


    public TimeSlotDto(Integer id, Integer employeeId, LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
