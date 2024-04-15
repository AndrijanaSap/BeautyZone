package com.beautyzone.beautysalonapp.repository;

import com.beautyzone.beautysalonapp.domain.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
    List<Holiday> findAllByEmployee_Id(Integer employeeId);
}
