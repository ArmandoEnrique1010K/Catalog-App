package com.backend.electronic.models.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.CategoryFeatureNameDto;
import com.backend.electronic.models.dto.CategoryFeaturesIdsDto;
import com.backend.electronic.models.entities.CategoryFeature;

@Component
@Mapper(componentModel = "spring")
public interface CategoryFeatureDtoMapper {

    // @Mapping(source = "category.id", target = "idCategory")
    @Mapping(source = "feature.id", target = "idFeature")
    @Mapping(source = "feature.name", target = "nameFeature")
    CategoryFeatureNameDto toDtoNameFeature(CategoryFeature categoryFeature);

    @Mapping(source = "category.id", target = "idCategory")
    @Mapping(source = "feature.id", target = "idFeature")
    CategoryFeaturesIdsDto toDtoIds(CategoryFeature categoryFeature);
}
