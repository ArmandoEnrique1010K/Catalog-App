package com.backend.electronic.models.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.FeatureValueDto;
import com.backend.electronic.models.entities.FeatureValue;

@Component
@Mapper(componentModel = "spring")
public interface FeatureValueDtoMapper {
    FeatureValueDto toDto(FeatureValue featureValue);
}
