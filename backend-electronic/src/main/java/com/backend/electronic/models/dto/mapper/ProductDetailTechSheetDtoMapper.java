package com.backend.electronic.models.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.ProductDetailDto;
import com.backend.electronic.models.entities.Product;
import com.backend.electronic.models.entities.ProductFeature;

@Component
@Mapper(componentModel = "spring")
public interface ProductDetailTechSheetDtoMapper {
    @Mapping(source = "brand.id", target = "idBrand")
    @Mapping(source = "category.id", target = "idCategory")
    @Mapping(source = "image.name", target = "nameImage")
    @Mapping(source = "productFeature", target = "feature", qualifiedByName = "mapFirstFeatureName")
    @Mapping(source = "productFeature", target = "value", qualifiedByName = "mapFirstFeatureValue")
    ProductDetailDto toDto(Product product);

    // TODO: INVESTIGAR LA POSIBILIDAD DE USAR UNA LISTA
    @Named("mapFirstFeatureName")
    default String mapFirstFeatureName(List<ProductFeature> productFeatures) {
        return productFeatures != null && !productFeatures.isEmpty() ? productFeatures.get(0).getFeature().getName()
                : null;
    }

    @Named("mapFirstFeatureValue")
    default String mapFirstFeatureValue(List<ProductFeature> productFeatures) {
        return productFeatures != null && !productFeatures.isEmpty()
                ? productFeatures.get(0).getFeatureValue().getValue()
                : null;
    }
}
