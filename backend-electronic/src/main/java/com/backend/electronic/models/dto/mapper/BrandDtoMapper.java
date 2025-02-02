package com.backend.electronic.models.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.BrandDto;
import com.backend.electronic.models.entities.Brand;

// Clase de tipo Mapper para mapear los campos de la entidad hacia el DTO 
// el uso de la dependencia MapStruck simplifica bastante el codigo
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

// https://www.youtube.com/watch?v=gBaCpsqsWfY&ab_channel=DebuggeandoIdeas
// https://javarush.com/es/groups/posts/es.3698.qu-es-mapstruct-y-cmo-configurarlo-correctamente-para-pruebas-unitarias-en-aplicaciones-spr
