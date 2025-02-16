package com.backend.electronic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.backend.electronic.models.dto.CategoryFeatureNameDto;
import com.backend.electronic.models.dto.CategoryFeaturesIdsDto;
import com.backend.electronic.models.dto.mapper.CategoryFeatureDtoMapper;
import com.backend.electronic.models.entities.Category;
import com.backend.electronic.models.entities.CategoryFeature;
import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.models.entities.ProductFeature;
import com.backend.electronic.repositories.CategoryFeatureRepository;
import com.backend.electronic.repositories.CategoryRepository;
import com.backend.electronic.repositories.FeatureRepository;
import com.backend.electronic.validators.CustomValidator;

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

        @Autowired
        private CustomValidator validationService;

        @Override
        public List<CategoryFeatureNameDto> findAllByCategoryId(Long id) {
                List<CategoryFeature> categoryFeature = categoryFeatureRepository.findByCategoryId(id);
                return categoryFeature.stream().map(
                                categoryFeatureDtoMapper::toDtoNameFeature)
                                .toList();

        }

        @Override
        public CategoryFeaturesIdsDto save(CategoryFeaturesIdsDto categoryFeatureIdsDto) {
                // Buscar la categoría en la base de datos
                Category category = categoryRepository.findById(
                                categoryFeatureIdsDto.getIdCategory())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "La categoría con ID " + categoryFeatureIdsDto.getIdCategory()
                                                                + " no existe"));

                // Buscar la característica en la base de datos
                Feature feature = featureRepository.findById(
                                categoryFeatureIdsDto.getIdFeature())
                                .orElseThrow(
                                                () -> new IllegalArgumentException(
                                                                "La característica con ID " + categoryFeatureIdsDto
                                                                                .getIdFeature() + " no existe"));

                // Verificar si la relación ya existe para evitar duplicados
                Optional<CategoryFeature> existingRelation = categoryFeatureRepository.findByCategoryAndFeature(
                                category,
                                feature);

                if (existingRelation.isPresent()) {
                        // TODO: IMPLEMENTAR ESTE METODO EN LOS DEMÁS SERVICIOS
                        // APLICAR LA VALIDACIÓN EN EL SERVICIO, EVITA QUE SE SALTEE EL ID SI NO SE HA
                        // GUARDADO EL REGISTRO EN LA BASE DE DATOS
                        validationService.validateExistingRelation(existingRelation.isPresent());
                }

                // Crear y guardar la relación
                CategoryFeature categoryFeature = new CategoryFeature();
                categoryFeature.setCategory(category);
                categoryFeature.setFeature(feature);
                CategoryFeature result = categoryFeatureRepository.save(categoryFeature);

                // Retornar DTO con la información guardada
                return categoryFeatureDtoMapper.toDtoIds(result);
        }
}
