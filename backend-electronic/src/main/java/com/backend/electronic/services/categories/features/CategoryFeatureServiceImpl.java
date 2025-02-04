package com.backend.electronic.services.categories.features;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.electronic.models.dto.CategoryFeatureDto;
import com.backend.electronic.models.dto.mapper.CategoryFeatureDtoMapper;
import com.backend.electronic.models.entities.Category;
import com.backend.electronic.models.entities.CategoryFeature;
import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.repositories.CategoryFeatureRepository;
import com.backend.electronic.repositories.CategoryRepository;
import com.backend.electronic.repositories.FeatureRepository;

@Service
public class CategoryFeatureServiceImpl implements CategoryFeatureService {

    @Autowired
    private CategoryFeatureRepository categoryFeatureRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryFeatureDtoMapper categoryFeatureDtoMapper;

    @Override
    public List<CategoryFeatureDto> findAllByCategoryId(Long id) {
        List<CategoryFeature> categoryFeature = categoryFeatureRepository.findByCategoryId(id);
        return categoryFeature.stream().map(
                categoryFeatureDtoMapper::toDto)
                .toList();

    }

    @Override
    public CategoryFeatureDto save(Long idCategory, Long idFeature) {
        // Buscar la categoría en la base de datos
        Category category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new IllegalArgumentException("La categoría con ID " + idCategory + " no existe"));

        // Buscar la característica en la base de datos
        Feature feature = featureRepository.findById(idFeature)
                .orElseThrow(
                        () -> new IllegalArgumentException("La característica con ID " + idFeature + " no existe"));

        // Verificar si la relación ya existe para evitar duplicados
        Optional<CategoryFeature> existingRelation = categoryFeatureRepository.findByCategoryAndFeature(category,
                feature);
        if (existingRelation.isPresent()) {
            throw new IllegalStateException("La categoría ya tiene asignada esta característica");
        }

        // Crear y guardar la relación
        CategoryFeature categoryFeature = new CategoryFeature();
        categoryFeature.setCategory(category);
        categoryFeature.setFeature(feature);

        // CategoryFeature savedCategoryFeature =
        // categoryFeatureRepository.save(categoryFeature);

        // Retornar DTO con la información guardada
        // return new CategoryFeatureDto(savedCategoryFeature.getId(),
        // savedCategoryFeature.getCategory().getId(),
        // savedCategoryFeature.getFeature().getId());

        return categoryFeatureDtoMapper.toDto(categoryFeatureRepository.save(categoryFeature));

    }

}
