package com.beautyzone.beautysalonapp.rest.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class HolidayRequestDto {
    private String id;
    private String name;
    private String holidayType;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    List<Integer> employees;
}
