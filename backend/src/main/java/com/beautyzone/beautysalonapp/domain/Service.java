package com.beautyzone.beautysalonapp.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer durationInMinutes;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
