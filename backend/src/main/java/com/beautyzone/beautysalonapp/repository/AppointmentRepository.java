package com.beautyzone.beautysalonapp.repository;

import com.beautyzone.beautysalonapp.domain.Appointment;
import com.beautyzone.beautysalonapp.domain.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAllByClient_Id(Integer clientId);

    List<Appointment> findAllByEmployee_Id(Integer employeeId);

}
