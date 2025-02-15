package com.backend.electronic.services.features;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.electronic.models.dto.FeatureDto;
import com.backend.electronic.models.dto.mapper.FeatureDtoMapper;
import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.repositories.FeatureRepository;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private FeatureDtoMapper featureDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<FeatureDto> findAllByStatusTrue() {
        List<Feature> features = featureRepository.findByStatusTrue();
        return features.stream().map(
                featureDtoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeatureDto> findById(Long id) {
        Optional<Feature> optionalFeature = featureRepository.findById(id);
        if (optionalFeature.isPresent() && optionalFeature.get().getStatus().equals(true)) {
            return optionalFeature.map(featureDtoMapper::toDto);
        } else {
            return Optional.empty();
        }

    }

    @Override
    @Transactional
    public FeatureDto save(Feature feature) {
        feature.setName(feature.getName());
        feature.setStatus(true);
        return featureDtoMapper.toDto(featureRepository.save(feature));
    }

    @Override
    @Transactional
    public Optional<FeatureDto> update(Feature feature, Long id) {
        Optional<Feature> optional = featureRepository.findById(id);

        Feature featureOptional = null;

        if (optional.isPresent()) {
            Feature featureDb = optional.orElseThrow();

            featureDb.setName(feature.getName());
            featureOptional = featureRepository.save(featureDb);
        }

        return Optional.ofNullable(featureOptional).map(featureDtoMapper::toDto);
    }

    @Override
    @Transactional
    public void disable(Long id) {
        Optional<Feature> optional = featureRepository.findById(id);

        if (optional.isPresent()) {
            Feature featureDb = optional.orElseThrow();
            featureDb.setStatus(false);
            featureRepository.save(featureDb);
        }
    }

}
