package com.beautyzone.beautysalonapp.rest.mapper;

import com.beautyzone.beautysalonapp.domain.Category;
import com.beautyzone.beautysalonapp.rest.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryDtoToCategory(CategoryDto categoryDto);
//    CategoryDto categoryToCategoryDto(Category category);
    CategoryDto categoryToCategoryDtoWithServiceDtos(Category category);

    List<CategoryDto> categoriesToCategoryDtos(List<Category> categories);
}
