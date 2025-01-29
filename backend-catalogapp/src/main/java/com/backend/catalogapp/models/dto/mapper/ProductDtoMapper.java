package com.backend.catalogapp.models.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.backend.catalogapp.models.dto.ProductDetailDto;
import com.backend.catalogapp.models.dto.ProductsListDto;
import com.backend.catalogapp.models.entities.Product;

@Component
@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    @Mapping(source = "image.id", target = "idImage")
    @Mapping(source = "image.name", target = "nameImage")
    ProductsListDto toListDto(Product product);

    // En algunos casos se tiene que mapear de forma manual
    @Mapping(source = "brand.id", target = "idBrand")
    @Mapping(source = "category.id", target = "idCategory")
    ProductDetailDto toDetailDto(Product product);
}
