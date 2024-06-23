package com.beautyzone.beautysalonapp.service;

import com.beautyzone.beautysalonapp.rest.dto.AvailabilityRequestDto;
import com.beautyzone.beautysalonapp.rest.dto.AvailabilityResponseDto;

import java.util.List;

public interface TimeSlotService {

    List<AvailabilityResponseDto> checkAvailability(AvailabilityRequestDto availabilityRequestDto);

}
