package com.backend.catalogapp.models.dto.mapper;

// import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.backend.catalogapp.models.dto.BrandDto;
import com.backend.catalogapp.models.entities.Brand;

// https://www.youtube.com/watch?v=gBaCpsqsWfY&ab_channel=DebuggeandoIdeas
// https://javarush.com/es/groups/posts/es.3698.qu-es-mapstruct-y-cmo-configurarlo-correctamente-para-pruebas-unitarias-en-aplicaciones-spr

// Sintaxis para usar mapStruct
@Component
@Mapper(componentModel = "spring")
public interface BrandDtoMapper {

    // De Entidad a DTO

    // No recomendado para listas de entidades
    // List<BrandDto> toDto(List<Brand> brands);

    // Por una entidad
    BrandDto toDto(Brand brand);
}
