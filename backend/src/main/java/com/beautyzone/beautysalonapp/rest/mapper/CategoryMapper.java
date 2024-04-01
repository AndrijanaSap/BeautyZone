package com.beautyzone.beautysalonapp.rest.mapper;
import com.beautyzone.beautysalonapp.domain.Category;
import com.beautyzone.beautysalonapp.rest.dto.CategoryDto;

import com.beautyzone.beautysalonapp.rest.dto.CategoryWithServicesDto;
import com.beautyzone.beautysalonapp.rest.dto.CategoryWithServicesWithEmployeesDto;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryDtoToCategory(CategoryDto categoryDto);
    CategoryDto categoryToCategoryDto(Category category);

    CategoryWithServicesDto categoryToCategoryWithServicesDto(Category category);

    List<CategoryDto> categoriesToCategoryDtos(List<Category> categories);

    List<CategoryWithServicesDto> categoriesToCategoryWithServicesDtos(List<Category> categories);
}
