package com.beautyzone.beautysalonapp.repository;

import com.beautyzone.beautysalonapp.constants.Role;
import com.beautyzone.beautysalonapp.domain.Category;
//import com.beautyzone.beautysalonapp.domain.Employee;
import com.beautyzone.beautysalonapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<User, Integer> {

    @Query("SELECT e FROM User e JOIN e.services s WHERE s.id = :serviceId")
    List<User> findEmployeesByServiceId(Integer serviceId);
    List<User> findAllByRole(Role role);

}
