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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String jobPosition;
    @OneToMany(mappedBy = "category", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private List<Service> services;
}
