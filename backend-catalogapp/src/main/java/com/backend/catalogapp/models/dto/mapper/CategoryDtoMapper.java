package com.backend.catalogapp.models.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.backend.catalogapp.models.dto.CategoryDto;
import com.backend.catalogapp.models.entities.Category;

@Component
@Mapper(componentModel = "spring")

public interface CategoryDtoMapper {

    CategoryDto toDto(Category category);
}
