package com.beautyzone.beautysalonapp.repository;

import com.beautyzone.beautysalonapp.domain.Category;
import com.beautyzone.beautysalonapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findCategoryById(Integer id);
    void deleteCategoryById(Integer id);

    List<Category> findAll();

}
