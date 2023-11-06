package com.beautyzone.beautysalonapp.repository;

import com.beautyzone.beautysalonapp.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserById(Integer id);

    Optional<User> findUserByEmail(String email);

    void deleteUserById(Integer id);


}
