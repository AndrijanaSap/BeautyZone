package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.constants.TimeSlotType;
import com.beautyzone.beautysalonapp.domain.Timeslot;
//import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.repository.TimeSlotRepository;
import com.beautyzone.beautysalonapp.repository.EmployeeRepository;
import com.beautyzone.beautysalonapp.repository.ServiceRepository;
import com.beautyzone.beautysalonapp.rest.dto.*;
import com.beautyzone.beautysalonapp.rest.mapper.TimeSlotMapper;
import com.beautyzone.beautysalonapp.rest.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final ServiceRepository serviceRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceService serviceService;
    private final CategoryMapper categoryMapper;
    private final TimeSlotMapper timeSlotMapper;
    private final int timeSlotUnitInMinutes = 30;

    public List<AvailabilityResponseDto> checkAvailability(AvailabilityRequestDto availabilityRequestDto) {
        List<AvailabilityResponseDto> availabilityResponseDtos = new ArrayList<>();
        List<Integer> employeeIds = new ArrayList<>();
        LocalDateTime from = convert(availabilityRequestDto.getPeriodFrom());
        LocalDateTime to = convert(availabilityRequestDto.getPeriodTo());

        if (availabilityRequestDto.getEmployeeId() != null) {
            employeeIds.add(availabilityRequestDto.getEmployeeId());
        } else {
            employeeIds = employeeRepository.findEmployeesByServiceId(availabilityRequestDto.getServiceId()).stream().map(User::getId).collect(Collectors.toList());
        }

        com.beautyzone.beautysalonapp.domain.Service service = serviceRepository.findById(availabilityRequestDto.getServiceId())
                .orElseThrow(() -> new NoSuchElementException("Service not found with id: " + availabilityRequestDto.getServiceId()));

        List<Timeslot> timeSlots = timeSlotRepository.findByStartTimeBetweenAndTimeSlotTypeAndEmployeeIdInOrderByEmployeeAscStartTimeAsc(from, to, TimeSlotType.AVAILABLE.toString(), employeeIds, availabilityRequestDto.getIncludeAppointmentId());

        //da dodademe vekje zafateni timeslotovi ako ima potreba

        LocalDateTime currentDateTime = convert(availabilityRequestDto.getPeriodFrom());
        List<CombinationDto> combinationDtos = new ArrayList<>();

        for (int i = 0; i < timeSlots.size(); i++) {
            LocalDateTime currTimeSlotDateTime = timeSlots.get(i).getStartTime();
            if (currentDateTime.toLocalDate().equals(currTimeSlotDateTime.toLocalDate())) {
                if(combinationDtos.stream().noneMatch(item -> item.getStartDateTime().isEqual(currTimeSlotDateTime))){
                    List<Integer> timeSlotIds = new ArrayList<>();
//                Integer sumOfDurations = (int) Duration.between(timeSlots.get(i).getStartTime(), timeSlots.get(i).getEndTime()).toMinutes();

                    generateCombination(timeSlotIds, timeSlotUnitInMinutes, timeSlots, i, service.getDurationInMinutes());
                    if (!timeSlotIds.isEmpty()) {
                        CombinationDto combinationDto = new CombinationDto();
                        combinationDto.setStartDateTime(timeSlots.get(i).getStartTime());
                        combinationDto.setEmployeeId(timeSlots.get(i).getEmployee().getId());
                        combinationDto.setTimeSlotIds(timeSlotIds);
                        combinationDtos.add(combinationDto);
                    }
                }

            } else {
                AvailabilityResponseDto availabilityResponseDto = new AvailabilityResponseDto();
                availabilityResponseDto.setDate(currentDateTime);
                availabilityResponseDto.setCombinationDtos(combinationDtos);
                availabilityResponseDtos.add(availabilityResponseDto);
                combinationDtos = new ArrayList<>();
                currentDateTime = currentDateTime.plusDays(1);
                i--;
            }
        }

        //Remove current combination if reschedule

        // Add last day
        AvailabilityResponseDto availabilityResponseDto = new AvailabilityResponseDto();
        availabilityResponseDto.setDate(currentDateTime);
        availabilityResponseDto.setCombinationDtos(combinationDtos);
        availabilityResponseDtos.add(availabilityResponseDto);

        return availabilityResponseDtos;
    }

    private void generateCombination(List<Integer> timeSlotIds, Integer sumOfDurations, List<Timeslot> timeSlots, int i, Integer durationOfService) {
        if (sumOfDurations.equals(durationOfService)) {
            timeSlotIds.add(timeSlots.get(i).getId());
            return;
        }
        if (i + 1 < timeSlots.size() && timeSlots.get(i + 1).getStartTime().isEqual(timeSlots.get(i).getEndTime()) // is it consecutive number?
                && timeSlots.get(i + 1).getEmployee().getId().equals(timeSlots.get(i).getEmployee().getId())) {  //is the same employee?
            timeSlotIds.add(timeSlots.get(i).getId());
            generateCombination(timeSlotIds, sumOfDurations + timeSlotUnitInMinutes, timeSlots, i + 1, durationOfService);
        }
    }

    public static LocalDateTime convert(Date date) {
        // Convert Date to Instant
        Instant instant = date.toInstant();

        // Create ZonedDateTime from Instant
        // Use a specific time zone if necessary, otherwise use system default
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

        // Convert ZonedDateTime to LocalDateTime
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        return localDateTime;
    }


}