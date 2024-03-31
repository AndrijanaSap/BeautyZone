package com.beautyzone.beautysalonapp.repository;

import com.beautyzone.beautysalonapp.domain.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<Timeslot, Integer> {
//    @Query("SELECT a FROM Timeslot a ORDER BY a.startTime DESC")
//    Optional<Timeslot> findLastAppointmentByStartTime();
//    @Query("SELECT e FROM Employee e JOIN e.services s WHERE s.id = :serviceId")
//    List<Appointment> findAllFreeAppointmentsByEmployee(Integer employeeId, Integer durationInMinutes);

//    @Query(nativeQuery = true,value ="SELECT a.employee.id, a.id, a.startTime " +
//            "FROM Appointment a " +
//            "WHERE a.startTime > :startDate " +
//            "AND a.endTime < :endDate " +
//            "AND a.appointmentType = :appointmentType " +
//            "AND a.employee.id IN :employeeIds " +
//            "GROUP BY a.employee.id, a.id, a.startTime " +
//            "ORDER BY a.employee.id, a.startTime")
//    @Query(nativeQuery = true,value ="SELECT  date(a.start_time), a.employee_id, a.id, a.start_time FROM beautysalon.appointment as a " +
//            "WHERE a.start_time > '2024-02-28 22:37:35.029529' " +
//            "AND a.end_time < '2024-02-29 22:37:35.029529' " +
//            "AND a.appointment_type = 'AVAILABLE' " +
//            "AND a.employee_id IN (1, 2) " +
//            "GROUP BY date(a.start_time), a.employee_id, a.id, a.start_time")
//@Query(nativeQuery = true,value ="SELECT * FROM beautysalon.appointment as a " +
//        "WHERE a.start_time > '2024-02-28 22:37:35.029529' " +
//        "AND a.end_time < '2024-02-29 22:37:35.029529' " +
//        "AND a.appointment_type = 'AVAILABLE' " +
//        "AND a.employee_id IN (1, 2) " +
//        "ORDER BY a.start_time")
//    List<Appointment> findAvailableAppointmentsByEmployeeIdsGroupedByEmployeeIdAndSortedByStartTime(
//            @Param("startDate") LocalDateTime startDate,
//            @Param("endDate") LocalDateTime endDate,
//            @Param("appointmentType") AppointmentType appointmentType,
//            @Param("employeeIds") List<Integer> employeeIds
//    );
//    List<Appointment> findByStartTimeBetweenAndAppointmentTypeAndEmployeeIdInOrderByEmployeeAscStartTimeAsc(
//            LocalDateTime startDate, LocalDateTime endDate,
//            AppointmentType appointmentType, List<Integer> employeeIds);

    @Query(value = "SELECT * FROM beautysalon.timeslot t " +
            "WHERE t.start_time BETWEEN :startTime AND :endTime " +
            "AND t.time_slot_type = :timeSlotType " +
            "AND t.employee_id IN :employeeIds " +
            "ORDER BY DATE(t.start_time), t.employee_id, t.start_time", nativeQuery = true)
    List<Timeslot> findByStartTimeBetweenAndTimeSlotTypeAndEmployeeIdInOrderByEmployeeAscStartTimeAsc(
            LocalDateTime startTime, LocalDateTime endTime, String timeSlotType, List<Integer> employeeIds);

}
