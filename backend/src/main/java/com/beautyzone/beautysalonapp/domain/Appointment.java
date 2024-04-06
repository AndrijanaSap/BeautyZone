package com.beautyzone.beautysalonapp.domain;

import com.beautyzone.beautysalonapp.constants.AppointmentStatus;
import com.beautyzone.beautysalonapp.constants.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
    private String name;
    private String phoneNumber;
    private String email;
    private String note;
    private String ipAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Timeslot> timeslots;
}
