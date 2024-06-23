package com.beautyzone.beautysalonapp.service;

import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.rest.dto.ShiftRequestDto;
import com.beautyzone.beautysalonapp.rest.dto.ShiftWithEmployeeResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShiftService {

    ShiftWithEmployeeResponseDto findById(Integer id);

    List<ShiftWithEmployeeResponseDto> findAll();

    List<ShiftWithEmployeeResponseDto> findAllByEmployeeId(Integer id);

    void createShift(ShiftRequestDto shiftRequestDto) throws Exception;

    void updateShift(ShiftRequestDto shiftRequestDto) throws Exception;

    boolean deleteShiftById(Integer id);
}
