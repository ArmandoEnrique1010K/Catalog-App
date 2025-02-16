package com.backend.electronic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.electronic.models.dto.FeatureValueDto;
import com.backend.electronic.models.dto.mapper.FeatureValueDtoMapper;
import com.backend.electronic.models.entities.Category;
import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.models.entities.FeatureValue;
import com.backend.electronic.repositories.CategoryRepository;
import com.backend.electronic.repositories.FeatureRepository;
import com.backend.electronic.repositories.FeatureValueRepository;

@Service
public class FeatureValueServiceImpl implements FeatureValueService {

    @Autowired
    private FeatureValueRepository featureValueRepository;

    @Autowired
    private FeatureRepository featureRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FeatureValueDtoMapper featureValueDtoMapper;

    @Override
    public List<FeatureValueDto> findAllByFeatureId(Long id) {
        List<FeatureValue> featureValue = featureValueRepository.findByFeatureId(id);
        return featureValue.stream().map(
                featureValueDtoMapper::toDto)
                .toList();
    }

    @Override
    public List<FeatureValueDto> getFeatureValuesByCategoryAndFeature(Long categoryId, Long featureId) {
        List<FeatureValue> featureValue = featureValueRepository.findFeatureValuesByCategoryAndFeature(categoryId,
                featureId);

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        Optional<Feature> optionalFeature = featureRepository.findById(featureId);

        // Optional<Feature> optionalFeature = featureRepository.findById(id);
        // if (optionalFeature.isPresent() &&
        // optionalFeature.get().getStatus().equals(true)) {
        // return optionalFeature.map(featureDtoMapper::toDto);
        // } else {
        // return Optional.empty();
        // }

        if (optionalCategory.isPresent() && optionalCategory.get().getStatus().equals(true)
                && optionalFeature.isPresent() && optionalFeature.get().getStatus().equals(true)) {
            return featureValue.stream().map(
                    featureValueDtoMapper::toDto)
                    .toList();

        } else {
            // RETORNA UNA LISTA VACIA
            return List.of();
        }

        // return featureValue.stream().map(
        // featureValueDtoMapper::toDto)
        // .toList();

    }

    @Override
    public FeatureValueDto save(FeatureValue featureValue, Long id) {
        // Buscar la característica por su ID
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La característica con ID " + id + " no existe"));

        // Asignar la característica encontrada al FeatureValue
        featureValue.setFeature(feature);

        // Guardar y devolver el DTO
        return featureValueDtoMapper.toDto(featureValueRepository.save(featureValue));
    }

}
