package com.beautyzone.beautysalonapp.rest.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ShiftRequestDto {
    private String id;
    private ZonedDateTime shiftStart;
    private ZonedDateTime shiftEnd;
    private ZonedDateTime periodFrom;
    private ZonedDateTime periodTo;
    private String shiftType;

    List<Integer> employees;
}
