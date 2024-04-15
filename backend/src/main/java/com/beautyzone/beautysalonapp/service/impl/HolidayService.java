package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.constants.HolidayType;
import com.beautyzone.beautysalonapp.constants.PaymentMethod;
import com.beautyzone.beautysalonapp.constants.TimeSlotType;
import com.beautyzone.beautysalonapp.domain.Holiday;
import com.beautyzone.beautysalonapp.domain.Timeslot;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.repository.*;
import com.beautyzone.beautysalonapp.rest.dto.*;
import com.beautyzone.beautysalonapp.rest.mapper.HolidayMapper;
import com.beautyzone.beautysalonapp.rest.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayService {
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    private final HolidayRepository holidayRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final CategoryMapper categoryMapper;
    private final HolidayMapper holidayMapper;
    private final int timeSlotUnitInMinutes = 30;

    public HolidayWithEmployeeResponseDto findById(Integer id) {
        Holiday holiday = holidayRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Holiday with id: " + id + "not found."));
        return holidayMapper.holidayToHolidayWithEmployeeResponseDto(holiday);
    }

    public List<HolidayWithEmployeeResponseDto> findAll() {
        List<Holiday> holidays = holidayRepository.findAll();
        return holidayMapper.holidaysToHolidayWithEmployeeResponseDtos(holidays);
    }

    public List<HolidayWithEmployeeResponseDto> findAllByEmployeeId(Integer id) {
        List<Holiday> holidays = holidayRepository.findAllByEmployee_Id(id);
        return holidayMapper.holidaysToHolidayWithEmployeeResponseDtos(holidays);
    }

    public void createHoliday(HolidayRequestDto holidayRequestDto) throws Exception {
        List<Holiday> holidays = new ArrayList<>();

        for (Integer employeeId : holidayRequestDto.getEmployees()) {
            LocalDateTime localStartDateTime = holidayRequestDto.getStartDateTime().withZoneSameInstant(ZoneId.of("Europe/Skopje")).toLocalDateTime();
            LocalDateTime localEndDateTime =holidayRequestDto.getEndDateTime().withZoneSameInstant(ZoneId.of("Europe/Skopje")).toLocalDateTime();
            List<Timeslot> timeslots = timeSlotRepository.findByStartTimeBetweenAndEmployee_Id(
                    localStartDateTime ,
                    localEndDateTime,
                    employeeId);

            if (timeslots.isEmpty() || timeslots.stream().anyMatch(timeslot -> timeslot.getTimeSlotType().equals(TimeSlotType.SCHEDULED))) {
                throw new Exception("No available timeslots found for this period");
            }

            Holiday holiday = new Holiday();
            holiday.setName(holidayRequestDto.getName());
            holiday.setEmployee(employeeRepository.getReferenceById(employeeId));
            holiday.setHolidayType(HolidayType.valueOf(holidayRequestDto.getHolidayType()));
            holiday.setStartDateTime(localStartDateTime);
            holiday.setEndDateTime(localEndDateTime);

            Holiday holidayDb = holidayRepository.save(holiday);

            for (Timeslot timeslot : timeslots) {
                timeslot.setHoliday(holidayDb);
                timeslot.setTimeSlotType(TimeSlotType.SCHEDULED);
            }

            holiday.setTimeslots(timeslots);

            holidays.add(holiday);
        }
        if (!holidays.isEmpty())
            holidayRepository.saveAll(holidays);
    }

    public void updateHoliday(HolidayRequestDto holidayRequestDto) throws Exception {
        List<Holiday> holidays = new ArrayList<>();

        for (Integer employeeId : holidayRequestDto.getEmployees()) {
            LocalDateTime localStartDateTime = holidayRequestDto.getStartDateTime().withZoneSameInstant(ZoneId.of("Europe/Skopje")).toLocalDateTime();
            LocalDateTime localEndDateTime =holidayRequestDto.getEndDateTime().withZoneSameInstant(ZoneId.of("Europe/Skopje")).toLocalDateTime();
            List<Timeslot> newTimeslots = timeSlotRepository.findByStartTimeBetweenAndEmployee_Id(
                    localStartDateTime ,
                    localEndDateTime,
                    employeeId);

            if (newTimeslots.isEmpty() || newTimeslots.stream().anyMatch(timeslot -> timeslot.getTimeSlotType().equals(TimeSlotType.SCHEDULED))) {
                throw new Exception("No available timeslots found for this period");
            }

            Holiday holiday = holidayRepository.findById(Integer.valueOf(holidayRequestDto.getId()))
                    .orElseThrow(() -> new UsernameNotFoundException("Holiday not found"));

            List<Timeslot> oldTimeslots = holiday.getTimeslots();

            // Update the modified fields
            if (!holiday.getName().equals(holidayRequestDto.getName()))
                holiday.setName(holidayRequestDto.getName());

            if (!holiday.getStartDateTime().equals(holidayRequestDto.getStartDateTime()))
                holiday.setStartDateTime(localStartDateTime);

            if (!holiday.getEndDateTime().equals(holidayRequestDto.getEndDateTime()))
                holiday.setEndDateTime(localEndDateTime);

            if (!holiday.getHolidayType().equals(HolidayType.valueOf(holidayRequestDto.getHolidayType())))
                holiday.setHolidayType(HolidayType.valueOf(holidayRequestDto.getHolidayType()));

            if (!holiday.getEmployee().getId().equals(employeeId))
                holiday.setEmployee(employeeRepository.getReferenceById(employeeId));


            // Update the timeslots if they are modified
            if (!oldTimeslots.equals(newTimeslots)) {
                // Update the new timeslots with the new holiday and update the timeslot status

                for (Timeslot timeslot : newTimeslots) {
                    timeslot.setHoliday(holiday);
                    timeslot.setTimeSlotType(TimeSlotType.SCHEDULED);
                }

                holiday.setTimeslots(newTimeslots);

                // Make old timeslots available again
                for (Timeslot timeslot : oldTimeslots) {
                    timeslot.setHoliday(null);
                    timeslot.setTimeSlotType(TimeSlotType.AVAILABLE);
                }
            }

            holidays.add(holiday);
        }

        if (!holidays.isEmpty())
            holidayRepository.saveAll(holidays);

    }

    public boolean deleteHolidayById(Integer id) {
        try {
            Holiday holiday = holidayRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Holiday not found"));

            holiday.getTimeslots().forEach(timeslot -> {
                timeslot.setTimeSlotType(TimeSlotType.AVAILABLE);
                timeslot.setHoliday(null);
            });

            holidayRepository.save(holiday);

            holidayRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return !holidayRepository.existsById(id);
    }
}