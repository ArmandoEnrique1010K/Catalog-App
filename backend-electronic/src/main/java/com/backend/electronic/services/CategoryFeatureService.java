package com.backend.electronic.services;

import java.util.List;

import com.backend.electronic.models.dto.CategoryFeatureNameDto;
import com.backend.electronic.models.dto.CategoryFeaturesIdsDto;

public interface CategoryFeatureService {
    // Listar todos los valores que corresponden a una categoria
    public List<CategoryFeatureNameDto> findAllByCategoryId(Long id);

    // Asignar una nueva caracteristica a una categoria (por ids)
    public CategoryFeaturesIdsDto save(CategoryFeaturesIdsDto categoryFeatureIdsDto);

}
