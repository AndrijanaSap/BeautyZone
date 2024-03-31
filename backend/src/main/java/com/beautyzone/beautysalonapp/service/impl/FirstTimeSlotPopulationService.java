package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.constants.AppointmentType;
import com.beautyzone.beautysalonapp.domain.Appointment;
import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.repository.AppointmentRepository;
import com.beautyzone.beautysalonapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Service
public class OneTimeAppointmentPopulationService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    public void prepopulateAppointmentsForOneYear() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        // Loop through each day
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            // Loop through each employee
            List<Employee> employees = employeeRepository.findAll();
            for (Employee employee : employees) {
                LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.of(8, 0)); // Start at 8 am
                LocalDateTime endDateTime = LocalDateTime.of(date, LocalTime.of(22, 0)); // End at 10 pm

                // Loop from start to end in 30-minute intervals
                while (startDateTime.isBefore(endDateTime)) {
                        Appointment appointment = new Appointment();
                        appointment.setEmployee(employee);
                        appointment.setCreationTime(LocalDateTime.now());
                        appointment.setStartTime(startDateTime);
                        appointment.setEndTime(startDateTime.plusMinutes(30));
                        appointment.setAppointmentType(AppointmentType.AVAILABLE);
                        appointmentRepository.save(appointment);

                    // Move to the next 30-minute interval
                    startDateTime = startDateTime.plusMinutes(30);
                }
            }
        }
    }
}
