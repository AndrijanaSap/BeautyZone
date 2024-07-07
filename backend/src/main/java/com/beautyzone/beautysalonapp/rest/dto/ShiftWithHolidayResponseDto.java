package com.beautyzone.beautysalonapp.rest.dto;

import com.beautyzone.beautysalonapp.constants.ShiftType;
import com.beautyzone.beautysalonapp.domain.Holiday;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShiftWithHolidayResponseDto {
    private Integer id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private ShiftType shiftType;
    private HolidayWithEmployeeResponseDto holiday;
}
