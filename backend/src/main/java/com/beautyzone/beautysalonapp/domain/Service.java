package com.beautyzone.beautysalonapp.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String imgPath;
    private Integer durationInMinutes;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "employee_service",
            joinColumns = { @JoinColumn(name = "service_id") },
            inverseJoinColumns = { @JoinColumn(name = "employee_id") }
    )
    private List<User> employees;

    @OneToMany(mappedBy = "service", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Appointment> appointments;
}
