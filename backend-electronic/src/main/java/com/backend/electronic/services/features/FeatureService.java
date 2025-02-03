package com.backend.electronic.services.features;

import java.util.List;
import java.util.Optional;

import com.backend.electronic.models.dto.FeatureDto;
import com.backend.electronic.models.entities.Feature;

public interface FeatureService {
    public List<FeatureDto> findAllByStatusTrue();

    public Optional<FeatureDto> findById(Long id);

    public FeatureDto save(Feature feature);

    public Optional<FeatureDto> update(Feature feature, Long id);

    public void disable(Long id);

}
