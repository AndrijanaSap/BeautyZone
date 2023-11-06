package com.beautyzone.beautysalonapp.repository;

import com.beautyzone.beautysalonapp.domain.Service;
import com.beautyzone.beautysalonapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    Optional<Service> findServiceById(Integer id);
    void deleteServiceById(Integer id);

}
