package com.beautyzone.beautysalonapp.rest.dto;
import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.domain.Service;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class AvailabilityResponseDto {
    private Date date;
    private List<TimeSlotDto> timeSlots;

    public AvailabilityResponseDto(Date date, List<LocalDateTime> timeSlots) {
        this.date = date;
        this.timeSlots = timeSlots;
    }
}
