package com.backend.electronic.services.features.values;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.electronic.models.dto.FeatureValueDto;
import com.backend.electronic.models.dto.mapper.FeatureValueDtoMapper;
import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.models.entities.FeatureValue;
import com.backend.electronic.repositories.FeatureRepository;
import com.backend.electronic.repositories.FeatureValueRepository;

@Service
public class FeatureValueServiceImpl implements FeatureValueService {

    @Autowired
    private FeatureValueRepository featureValueRepository;

    @Autowired
    private FeatureRepository featureRepository;

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
