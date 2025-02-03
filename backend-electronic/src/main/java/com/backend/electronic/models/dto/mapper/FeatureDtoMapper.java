package com.backend.electronic.models.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.FeatureDto;
import com.backend.electronic.models.entities.Feature;

@Component
@Mapper(componentModel = "spring")
public interface FeatureDtoMapper {
    FeatureDto toDto(Feature feature);
}
