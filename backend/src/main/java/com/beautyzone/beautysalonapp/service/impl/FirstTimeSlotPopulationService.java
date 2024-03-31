package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.constants.Role;
import com.beautyzone.beautysalonapp.constants.TimeSlotType;
//import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.domain.Timeslot;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.repository.TimeSlotRepository;
import com.beautyzone.beautysalonapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Service
public class FirstTimeSlotPopulationService {
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    public void prepopulateTimeSlotsForOneYear() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        // Loop through each day
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            // Loop through each employee
            List<User> employees = employeeRepository.findAllByRole(Role.EMPLOYEE);
            for (User employee : employees) {
                LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.of(8, 0)); // Start at 8 am
                LocalDateTime endDateTime = LocalDateTime.of(date, LocalTime.of(22, 0)); // End at 10 pm

                // Loop from start to end in 30-minute intervals
                while (startDateTime.isBefore(endDateTime)) {
                    Timeslot timeSlot = new Timeslot();
                        timeSlot.setEmployee(employee);
                        timeSlot.setCreationTime(LocalDateTime.now());
                        timeSlot.setStartTime(startDateTime);
                        timeSlot.setEndTime(startDateTime.plusMinutes(30));
                        timeSlot.setTimeSlotType(TimeSlotType.AVAILABLE);
                        timeSlotRepository.save(timeSlot);

                    // Move to the next 30-minute interval
                    startDateTime = startDateTime.plusMinutes(30);
                }
            }
        }
    }
}
