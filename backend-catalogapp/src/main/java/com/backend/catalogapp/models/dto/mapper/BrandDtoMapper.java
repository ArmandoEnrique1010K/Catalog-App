package com.backend.catalogapp.models.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.backend.catalogapp.models.dto.BrandDto;
import com.backend.catalogapp.models.entities.Brand;

// Sintaxis para usar mapStruct
@Mapper(componentModel = "spring")
public interface BrandDtoMapper {

    // De Entidad a DTO
    BrandDto toDto(Brand brand);

    List<BrandDto> toDto(List<Brand> brands);

}
