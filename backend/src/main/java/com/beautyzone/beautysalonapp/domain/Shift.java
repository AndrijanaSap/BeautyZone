package com.beautyzone.beautysalonapp.domain;
import com.beautyzone.beautysalonapp.constants.HolidayType;
import com.beautyzone.beautysalonapp.constants.ShiftType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    private LocalDateTime shiftStart;

    private LocalDateTime shiftEnd;

    @Enumerated(EnumType.STRING)
    private ShiftType shiftType;

    @OneToMany(mappedBy = "shift", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Timeslot> timeslots;
}
