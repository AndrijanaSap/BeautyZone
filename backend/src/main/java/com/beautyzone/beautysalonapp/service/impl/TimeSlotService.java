package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.constants.AppointmentType;
import com.beautyzone.beautysalonapp.domain.Appointment;
import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.repository.AppointmentRepository;
import com.beautyzone.beautysalonapp.repository.EmployeeRepository;
import com.beautyzone.beautysalonapp.repository.ServiceRepository;
import com.beautyzone.beautysalonapp.rest.dto.*;
import com.beautyzone.beautysalonapp.rest.mapper.AppointmentMapper;
import com.beautyzone.beautysalonapp.rest.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceService serviceService;
    private final CategoryMapper categoryMapper;
    private final AppointmentMapper appointmentMapper;
    private final int timeSlotUnitInMinutes = 30;

    public List<AppointmentResponseDto> findAll() throws Exception {
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            throw new Exception("No appointment found!");
        }
//        return categoryMapper.categoriesToCategoryDtos(categories);
        return null;
    }

    public AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto) {
        try {
            Appointment appointment = appointmentRepository.save(new Appointment());
            return appointmentMapper.appointmentToAppointmentResponseDto(appointment);
        } catch (Exception e) {
            return null;
        }
    }

    public List<AvailabilityResponseDto> checkAvailability(AvailabilityRequestDto availabilityRequestDto) {
        List<AvailabilityResponseDto> availabilityResponseDtos = new ArrayList<>();
        List<Integer> employeeIds = new ArrayList<>();
        LocalDateTime from = convert(availabilityRequestDto.getPeriodFrom());
        LocalDateTime to = convert(availabilityRequestDto.getPeriodTo());

        if (availabilityRequestDto.getEmployeeId() != null) {
            employeeIds.add(availabilityRequestDto.getEmployeeId());
        } else {
            employeeIds = employeeRepository.findEmployeesByServiceId(availabilityRequestDto.getServiceId()).stream().map(Employee::getId).collect(Collectors.toList());
        }

        com.beautyzone.beautysalonapp.domain.Service service = serviceRepository.findById(availabilityRequestDto.getServiceId())
                .orElseThrow(() -> new NoSuchElementException("Service not found with id: " + availabilityRequestDto.getServiceId()));

        List<Appointment> appointments = appointmentRepository.findByStartTimeBetweenAndAppointmentTypeAndEmployeeIdInOrderByEmployeeAscStartTimeAsc(from, to, AppointmentType.AVAILABLE.toString(), employeeIds);

        LocalDateTime currentDateTime = convert(availabilityRequestDto.getPeriodFrom());
        List<CombinationDto> combinationDtos = new ArrayList<>();

        for (int i = 0; i < appointments.size(); i++) {
            LocalDateTime currAppointmentDateTime = appointments.get(i).getStartTime();
            if (currentDateTime.toLocalDate().equals(currAppointmentDateTime.toLocalDate())) {
                if(combinationDtos.stream().noneMatch(item -> item.getStartDateTime().isEqual(currAppointmentDateTime))){
                    List<Integer> appointmentIds = new ArrayList<>();
//                Integer sumOfDurations = (int) Duration.between(appointments.get(i).getStartTime(), appointments.get(i).getEndTime()).toMinutes();

                    generateCombination(appointmentIds, timeSlotUnitInMinutes, appointments, i, service.getDurationInMinutes());
                    if (!appointmentIds.isEmpty()) {
                        CombinationDto combinationDto = new CombinationDto();
                        combinationDto.setStartDateTime(appointments.get(i).getStartTime());
                        combinationDto.setEmployeeId(appointments.get(i).getEmployee().getId());
                        combinationDto.setAppointmentIds(appointmentIds);
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

        // Add last day
        AvailabilityResponseDto availabilityResponseDto = new AvailabilityResponseDto();
        availabilityResponseDto.setDate(currentDateTime);
        availabilityResponseDto.setCombinationDtos(combinationDtos);
        availabilityResponseDtos.add(availabilityResponseDto);

        return availabilityResponseDtos;
    }

    private void generateCombination(List<Integer> appointmentIds, Integer sumOfDurations, List<Appointment> appointments, int i, Integer durationOfService) {
        if (sumOfDurations.equals(durationOfService)) {
            appointmentIds.add(appointments.get(i).getId());
            return;
        }
        if (i + 1 < appointments.size() && appointments.get(i + 1).getStartTime().isEqual(appointments.get(i).getEndTime()) // is it consecutive number?
                && appointments.get(i + 1).getEmployee().getId().equals(appointments.get(i).getEmployee().getId()) //is the same employee?
        ) {
            appointmentIds.add(appointments.get(i).getId());
            generateCombination(appointmentIds, sumOfDurations + timeSlotUnitInMinutes, appointments, i + 1, durationOfService);
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