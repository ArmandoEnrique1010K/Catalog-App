package com.backend.electronic.models.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.CategoryDto;
import com.backend.electronic.models.entities.Category;

@Component
@Mapper(componentModel = "spring")

public interface CategoryDtoMapper {

    CategoryDto toDto(Category category);
}
