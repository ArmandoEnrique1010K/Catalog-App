package com.backend.catalogapp.models.dto.mapper;

import org.mapstruct.Mapper;

import com.backend.catalogapp.models.dto.CategoryDto;
import com.backend.catalogapp.models.entities.Category;

@Mapper(componentModel = "spring")

public interface CategoryDtoMapper {

    CategoryDto toDto(Category category);
}
