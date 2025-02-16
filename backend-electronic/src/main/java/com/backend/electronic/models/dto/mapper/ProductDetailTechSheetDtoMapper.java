package com.backend.electronic.models.dto.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.ProductDetailTechSheetDto;
import com.backend.electronic.models.entities.Product;
import com.backend.electronic.models.entities.ProductFeature;

@Component
@Mapper(componentModel = "spring")
public interface ProductDetailTechSheetDtoMapper {

    // @Mapping(target = "id", source = "id")
    @Mapping(target = "idBrand", source = "product.brand.id")
    @Mapping(target = "idCategory", source = "product.category.id")
    @Mapping(target = "nameImage", source = "product.productImage.name")
    // EL MISMO CUANDO SE QUIERE HACER MAPEAR UNA LISTA
    @Mapping(target = "techSheet", source = "productFeatures", qualifiedByName = "mapFeatureValuesToTechSheetDto")
    ProductDetailTechSheetDto toDto(Product product);

    @Named("mapFeatureValuesToTechSheetDto")
    default Map<String, String> mapFeatureValuesToTechSheetDto(List<ProductFeature> productFeatures) {
        return productFeatures.stream()
                .collect(Collectors.toMap(
                        pf -> pf.getFeature().getName(), // Key: Feature name
                        pf -> pf.getFeatureValue().getValue() // Value: Feature value
                ));
    }

}
