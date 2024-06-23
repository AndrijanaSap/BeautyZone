package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.domain.Category;
import com.beautyzone.beautysalonapp.exception.NoSuchElementException;
import com.beautyzone.beautysalonapp.repository.CategoryRepository;
import com.beautyzone.beautysalonapp.repository.ServiceRepository;
import com.beautyzone.beautysalonapp.rest.dto.*;
import com.beautyzone.beautysalonapp.rest.mapper.CategoryMapper;
import com.beautyzone.beautysalonapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ServiceRepository serviceRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAll() throws Exception {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new Exception("No category found!");
        }
        return categoryMapper.categoriesToCategoryDtos(categories);
    }
    @Override

    public List<CategoryWithServicesDto> getAllWithServices() throws Exception {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new Exception("No category found!");
        }
        return categoryMapper.categoriesToCategoryWithServicesDtos(categories);
    }
    @Override

    public CategoryWithServicesDto getCategoryWithServicesById(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
        return categoryMapper.categoryToCategoryWithServicesDto(category);
    }
    @Override
    public Integer addCategory(CategoryUpdateRequestDto request) throws IOException {
        var category = Category.builder()
                .name(request.getName())
                .jobPosition(request.getJobPosition())
                .build();

        categoryRepository.save(category);

        List<com.beautyzone.beautysalonapp.domain.Service> services = serviceRepository.findAllById(request.getServices());
        services.forEach(i -> i.setCategory(category));
        serviceRepository.saveAll(services);

        return  category.getId();
    }
    @Override

    public void updateCategory(CategoryUpdateRequestDto requestDto) throws IOException {
        Category category = categoryRepository.findById(requestDto.getId()).orElseThrow(() -> new UsernameNotFoundException("Category not found"));
        category.setName(requestDto.getName());
        category.setJobPosition(requestDto.getJobPosition());
        categoryRepository.save(category);

        List<com.beautyzone.beautysalonapp.domain.Service> services = serviceRepository.findAllById(requestDto.getServices());
        services.forEach(i -> i.setCategory(category));
        serviceRepository.saveAll(services);

    }
    @Override

    public boolean deleteCategoryById(Integer id) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Category not found"));
            category.getServices().forEach(i -> i.setCategory(null));
            categoryRepository.save(category);
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return !categoryRepository.existsById(id);
    }
}