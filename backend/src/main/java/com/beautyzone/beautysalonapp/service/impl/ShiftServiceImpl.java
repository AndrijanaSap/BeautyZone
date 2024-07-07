package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.constants.ShiftType;
import com.beautyzone.beautysalonapp.constants.TimeSlotType;
import com.beautyzone.beautysalonapp.domain.Shift;
import com.beautyzone.beautysalonapp.domain.Timeslot;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.repository.*;
import com.beautyzone.beautysalonapp.rest.dto.HolidayWithEmployeeResponseDto;
import com.beautyzone.beautysalonapp.rest.dto.ShiftRequestDto;
import com.beautyzone.beautysalonapp.rest.dto.ShiftWithHolidayResponseDto;
import com.beautyzone.beautysalonapp.rest.dto.ShiftWithEmployeeResponseDto;
import com.beautyzone.beautysalonapp.rest.mapper.CategoryMapper;
import com.beautyzone.beautysalonapp.rest.mapper.HolidayMapper;
import com.beautyzone.beautysalonapp.rest.mapper.ShiftMapper;
import com.beautyzone.beautysalonapp.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    private final ShiftRepository shiftRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final HolidayMapper holidayMapper;
    private final ShiftMapper shiftMapper;
    private final int timeSlotUnitInMinutes = 30;

    @Override
    public ShiftWithEmployeeResponseDto findById(Integer id) {
        Shift shift = shiftRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Shift with id: " + id + "not found."));
        return shiftMapper.shiftToShiftWithEmployeeResponseDto(shift);
    }

    @Override
    public List<ShiftWithEmployeeResponseDto> findAll() {
        List<Shift> shifts = shiftRepository.findAll();
        return shiftMapper.shiftsToShiftWithEmployeeResponseDtos(shifts);
    }

    @Override
    public List<ShiftWithEmployeeResponseDto> findAllByEmployeeId(Integer id) {
        List<Shift> shifts = shiftRepository.findAllByEmployee_Id(id);
        return shiftMapper.shiftsToShiftWithEmployeeResponseDtos(shifts);
    }

    public List<ShiftWithHolidayResponseDto> getAllWithHolidays(Integer id) {
        List<ShiftWithHolidayResponseDto> shiftWithHolidayResponseDtos = new ArrayList<>();
        List<Shift> shifts = shiftRepository.findAllByEmployee_Id(id);
        shifts.forEach(shift -> {
            if (shift.getTimeslots().getFirst().getHoliday() != null)
                shift.getTimeslots().getFirst().getHoliday().setEmployee(null);
            shiftWithHolidayResponseDtos.add(
                    ShiftWithHolidayResponseDto.builder()
                            .id(shift.getId())
                            .shiftStart(shift.getShiftStart())
                            .shiftEnd(shift.getShiftEnd())
                            .shiftType(shift.getShiftType())
                            .holiday(holidayMapper.holidayToHolidayWithEmployeeResponseDto(shift.getTimeslots().getFirst().getHoliday()))
                            .build()
            );
        });
        return shiftWithHolidayResponseDtos;
    }

    @Override
    public void createShift(ShiftRequestDto shiftRequestDto) throws Exception {
        for (Integer employeeId : shiftRequestDto.getEmployees()) {
            LocalDateTime localStartShiftTime = shiftRequestDto.getShiftStart().withZoneSameInstant(ZoneId.of("Europe/Skopje")).toLocalDateTime();
            LocalDateTime localEndShiftTime = shiftRequestDto.getShiftEnd().withZoneSameInstant(ZoneId.of("Europe/Skopje")).toLocalDateTime();
            LocalDateTime localStartDateTime = shiftRequestDto.getPeriodFrom().withZoneSameInstant(ZoneId.of("Europe/Skopje")).toLocalDateTime()
                    .with(LocalTime.of(localStartShiftTime.getHour(), localStartShiftTime.getMinute()));
            LocalDateTime localEndDateTime = shiftRequestDto.getPeriodTo().withZoneSameInstant(ZoneId.of("Europe/Skopje")).toLocalDateTime()
                    .with(LocalTime.of(localEndShiftTime.getHour(), localEndShiftTime.getMinute()));
            List<Timeslot> timeslots = timeSlotRepository.findByStartTimeBetweenAndEmployee_Id(
                    localStartDateTime,
                    localEndDateTime,
                    employeeId);

            if (!timeslots.isEmpty()) {
                throw new Exception("You selected a period with employee that already has shift! Please free those time slots or select different period.");
            }

            User employee = employeeRepository.getReferenceById(employeeId);

            // Loop from start to end in 30-minute intervals
            LocalDateTime currDate = localStartDateTime;
            Shift shift = new Shift();
            shift.setEmployee(employee);
            shift.setShiftStart(currDate);
            shift.setShiftEnd(currDate.with(LocalTime.of(localEndShiftTime.getHour(), localEndShiftTime.getMinute())));
            shift.setShiftType(ShiftType.valueOf(shiftRequestDto.getShiftType()));

            Shift shiftDb = shiftRepository.save(shift);

            while (localStartDateTime.isBefore(localEndDateTime)) {
                if (!currDate.toLocalDate().equals(localStartDateTime.toLocalDate())) {
                    currDate = localStartDateTime;
                    shift = new Shift();
                    shift.setEmployee(employee);
                    shift.setShiftStart(currDate);
                    shift.setShiftEnd(currDate.with(LocalTime.of(localEndShiftTime.getHour(), localEndShiftTime.getMinute())));
                    shift.setShiftType(ShiftType.valueOf(shiftRequestDto.getShiftType()));

                    shiftDb = shiftRepository.save(shift);
                }

                Timeslot timeSlot = new Timeslot();
                timeSlot.setEmployee(employee);
                timeSlot.setCreationTime(LocalDateTime.now());
                timeSlot.setStartTime(localStartDateTime);
                timeSlot.setEndTime(localStartDateTime.plusMinutes(30));
                timeSlot.setTimeSlotType(TimeSlotType.AVAILABLE);
                timeSlot.setShift(shiftDb);
                timeSlotRepository.save(timeSlot);

                // Move to the next 30-minute interval
                localStartDateTime = localStartDateTime.plusMinutes(30);

                // If reached end of shift move to next day
                if (localStartDateTime.toLocalTime().equals(localEndShiftTime.toLocalTime())) {
                    localStartDateTime = localStartDateTime.plusDays(1).with(LocalTime.of(localStartShiftTime.getHour(), localStartShiftTime.getMinute()));
                }
            }

//            shift.setTimeslots(timeslots);
//
//            shifts.add(shift);
        }
//        if (!shifts.isEmpty())
//            shiftRepository.saveAll(shifts);
    }

    @Override
    public void updateShift(ShiftRequestDto shiftRequestDto) throws Exception {
        List<Shift> shifts = new ArrayList<>();
        Shift shift = shiftRepository.findById(Integer.valueOf(shiftRequestDto.getId()))
                .orElseThrow(() -> new UsernameNotFoundException("Shift not found"));

        for (Integer employeeId : shiftRequestDto.getEmployees()) {
            LocalDateTime localStartDateTime = shiftRequestDto.getShiftStart().withZoneSameInstant(ZoneId.of("Europe/Skopje")).toLocalDateTime();
            LocalDateTime localEndDateTime = shiftRequestDto.getShiftEnd().withZoneSameInstant(ZoneId.of("Europe/Skopje")).toLocalDateTime();

            // Update TimeSlots if startDate and endDate are modified
            if (!shift.getShiftStart().equals(localStartDateTime) ||
                    !shift.getShiftEnd().equals(localEndDateTime)) {
                List<Timeslot> newTimeslots = timeSlotRepository.findByStartTimeBetweenAndEmployee_Id(
                        localStartDateTime,
                        localEndDateTime,
                        employeeId);

                if (newTimeslots.isEmpty() || newTimeslots.stream().anyMatch(timeslot -> timeslot.getTimeSlotType().equals(TimeSlotType.SCHEDULED))) {
                    throw new Exception("No available timeslots found for this period");
                }

                List<Timeslot> oldTimeslots = shift.getTimeslots();

                if (!oldTimeslots.equals(newTimeslots)) {
                    // Update the new timeslots with the new shift and update the timeslot status

                    for (Timeslot timeslot : newTimeslots) {
                        timeslot.setShift(shift);
                        timeslot.setTimeSlotType(TimeSlotType.SCHEDULED);
                    }

                    shift.setTimeslots(newTimeslots);

                    // Make old timeslots available again
                    for (Timeslot timeslot : oldTimeslots) {
                        timeslot.setShift(null);
                        timeslot.setTimeSlotType(TimeSlotType.AVAILABLE);
                    }
                }
            }

            // Update the modified fields

            if (!shift.getShiftStart().equals(localStartDateTime))
                shift.setShiftStart(localStartDateTime);

            if (!shift.getShiftEnd().equals(localEndDateTime))
                shift.setShiftEnd(localEndDateTime);

            if (!shift.getEmployee().getId().equals(employeeId))
                shift.setEmployee(employeeRepository.getReferenceById(employeeId));

            if (!shift.getShiftType().equals(ShiftType.valueOf(shiftRequestDto.getShiftType())))
                shift.setShiftType(ShiftType.valueOf(shiftRequestDto.getShiftType()));

            shifts.add(shift);
        }

        if (!shifts.isEmpty())
            shiftRepository.saveAll(shifts);

    }

    @Override
    public boolean deleteShiftById(Integer id) {
        try {
            Shift shift = shiftRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Shift not found"));

            shift.getTimeslots().forEach(timeslot -> {
                timeslot.setTimeSlotType(TimeSlotType.AVAILABLE);
                timeslot.setShift(null);
            });

            shiftRepository.save(shift);

            shiftRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return !shiftRepository.existsById(id);
    }
}