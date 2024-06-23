package com.beautyzone.beautysalonapp.domain;
import com.beautyzone.beautysalonapp.constants.TimeSlotType;
import com.beautyzone.beautysalonapp.constants.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "holiday_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Holiday holiday;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Shift shift;

    private LocalDateTime creationTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private TimeSlotType timeSlotType;
}
