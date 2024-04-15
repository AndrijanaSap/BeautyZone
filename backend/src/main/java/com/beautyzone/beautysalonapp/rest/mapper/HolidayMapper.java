package com.beautyzone.beautysalonapp.rest.mapper;

import com.beautyzone.beautysalonapp.domain.Holiday;
import com.beautyzone.beautysalonapp.rest.dto.HolidayWithEmployeeResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HolidayMapper {
    HolidayWithEmployeeResponseDto holidayToHolidayWithEmployeeResponseDto(Holiday holiday);

    List<HolidayWithEmployeeResponseDto> holidaysToHolidayWithEmployeeResponseDtos(List<Holiday> holidays);
}
