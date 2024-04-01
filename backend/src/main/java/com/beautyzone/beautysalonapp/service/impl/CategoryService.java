package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.domain.Category;
import com.beautyzone.beautysalonapp.repository.CategoryRepository;
import com.beautyzone.beautysalonapp.rest.dto.CategoryDto;
import com.beautyzone.beautysalonapp.rest.dto.CategoryWithServicesDto;
import com.beautyzone.beautysalonapp.rest.dto.CategoryWithServicesWithEmployeesDto;
import com.beautyzone.beautysalonapp.rest.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAll() throws Exception {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new Exception("No category found!");
        }
        return categoryMapper.categoriesToCategoryDtos(categories);
    }

    public List<CategoryWithServicesDto> getAllWithServices() throws Exception {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new Exception("No category found!");
        }
        return categoryMapper.categoriesToCategoryWithServicesDtos(categories);
    }
}