package com.beautyzone.beautysalonapp.service.impl;

import com.beautyzone.beautysalonapp.auth.ChangePasswordRequest;
import com.beautyzone.beautysalonapp.domain.Category;
import com.beautyzone.beautysalonapp.domain.User;
import com.beautyzone.beautysalonapp.repository.CategoryRepository;
import com.beautyzone.beautysalonapp.repository.UserRepository;
import com.beautyzone.beautysalonapp.rest.dto.CategoryDto;
import com.beautyzone.beautysalonapp.rest.dto.ServiceDto;
import com.beautyzone.beautysalonapp.rest.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> findAll() throws Exception {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new Exception("No category found!");
        }
        return categoryMapper.categoriesToCategoryDtos(categories);
    }
}