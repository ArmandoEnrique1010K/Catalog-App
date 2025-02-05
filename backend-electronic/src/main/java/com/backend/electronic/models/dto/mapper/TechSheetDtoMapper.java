package com.backend.electronic.models.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.entities.ProductFeature;

@Component
@Mapper(componentModel = "spring")
public interface TechSheetDtoMapper {

    @Mapping(source = "feature.name", target = "feature")
    @Mapping(source = "featureValue.value", target = "value")
    TechSheetDto toListDto(ProductFeature productFeature);

}
