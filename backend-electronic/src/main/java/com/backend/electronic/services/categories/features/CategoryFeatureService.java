package com.backend.electronic.services.categories.features;

import java.util.List;

import com.backend.electronic.models.dto.CategoryFeatureDto;

public interface CategoryFeatureService {
    // Listar todos los valores que corresponden a una categoria
    public List<CategoryFeatureDto> findAllByCategoryId(Long id);

    // Asignar una nueva caracteristica a una categoria (por ids)
    public CategoryFeatureDto save(Long idCategory, Long idFeature);

}
