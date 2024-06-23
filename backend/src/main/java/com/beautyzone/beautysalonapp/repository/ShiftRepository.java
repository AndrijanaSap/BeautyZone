package com.beautyzone.beautysalonapp.repository;

import com.beautyzone.beautysalonapp.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    List<Shift> findAllByEmployee_Id(Integer employeeId);
}
