package com.beautyzone.beautysalonapp.rest.mapper;

import com.beautyzone.beautysalonapp.domain.Shift;
import com.beautyzone.beautysalonapp.rest.dto.ShiftWithEmployeeResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShiftMapper {
    ShiftWithEmployeeResponseDto shiftToShiftWithEmployeeResponseDto(Shift shift);

    List<ShiftWithEmployeeResponseDto> shiftsToShiftWithEmployeeResponseDtos(List<Shift> shifts);
}
