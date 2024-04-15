package com.beautyzone.beautysalonapp.domain;

import com.beautyzone.beautysalonapp.constants.HolidayType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private HolidayType holidayType;

    @OneToMany(mappedBy = "holiday", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Timeslot> timeslots;
}
