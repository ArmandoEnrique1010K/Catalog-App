package com.backend.electronic.models.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.ProductDetailDto;
import com.backend.electronic.models.dto.ProductsListDto;
import com.backend.electronic.models.entities.Product;

@Component
@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    // En algunos casos se tiene que mapear de forma manual los campos que se
    // relacionan con otras entidades
    @Mapping(source = "productImage.name", target = "nameImage")
    @Mapping(source = "brand.name", target = "brandName")
    ProductsListDto toListDto(Product product);

    @Mapping(source = "brand.id", target = "idBrand")
    @Mapping(source = "category.id", target = "idCategory")
    @Mapping(source = "productImage.name", target = "nameImage")
    ProductDetailDto toDetailDto(Product product);
}
