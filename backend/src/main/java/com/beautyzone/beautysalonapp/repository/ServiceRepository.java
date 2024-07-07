package com.beautyzone.beautysalonapp.repository;

import com.beautyzone.beautysalonapp.domain.Service;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.rest.dto.ServiceWithCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Query("SELECT s FROM Service s " +
            "JOIN s.appointments a " +
            "WHERE a.createdAt >= :startDate " +
            "GROUP BY s " +
            "ORDER BY COUNT(a) DESC")
    List<Service> findTopThreeServicesWithMostAppointmentsWithStartDate(@Param("startDate") LocalDateTime startDate);

    Optional<Service> findServiceById(Integer id);
    void deleteServiceById(Integer id);


}
