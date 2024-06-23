package com.beautyzone.beautysalonapp.service;

import com.beautyzone.beautysalonapp.rest.dto.CategoryDto;
import com.beautyzone.beautysalonapp.rest.dto.CategoryUpdateRequestDto;
import com.beautyzone.beautysalonapp.rest.dto.CategoryWithServicesDto;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    /**
     * Retrieves all categories.
     *
     * @return List of CategoryDto objects.
     * @throws Exception if no categories are found.
     */
    List<CategoryDto> getAll() throws Exception;

    /**
     * Retrieves all categories with associated services.
     *
     * @return List of CategoryWithServicesDto objects.
     * @throws Exception if no categories are found.
     */
    List<CategoryWithServicesDto> getAllWithServices() throws Exception;

    /**
     * Retrieves a category with associated services by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return CategoryWithServicesDto object.
     * @throws com.beautyzone.beautysalonapp.exception.NoSuchElementException if category is not found.
     */
    CategoryWithServicesDto getCategoryWithServicesById(Integer id);

    /**
     * Adds a new category with associated services.
     *
     * @param request The CategoryUpdateRequestDto containing category and service information.
     * @return The ID of the newly added category.
     * @throws IOException if there's an I/O error.
     */
    Integer addCategory(CategoryUpdateRequestDto request) throws IOException;

    /**
     * Updates an existing category with associated services.
     *
     * @param requestDto The CategoryUpdateRequestDto containing updated category and service information.
     * @throws IOException if there's an I/O error.
     */
    void updateCategory(CategoryUpdateRequestDto requestDto) throws IOException;

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     * @return true if the category was successfully deleted, false otherwise.
     */
    boolean deleteCategoryById(Integer id);

}
