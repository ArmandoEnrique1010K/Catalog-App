package com.backend.electronic.models.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.ProductTechSheetDto;
import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.entities.FeatureValue;
import com.backend.electronic.models.entities.ProductFeature;

@Component
@Mapper(componentModel = "spring")

public interface ProductTechSheetDtoMapper {

    // DEBE CONTENER UN PRODUCTFEATURE
    @Mapping(target = "id", source = "id")
    @Mapping(target = "techSheet", source = "featureValue", qualifiedByName = "mapFeatureValuesToTechSheetDto")
    ProductTechSheetDto toDto(ProductFeature productFeature);

    // La entidad ProductFeature tiene el campo featureValue para la lista de
    // caracteristicas y valores

    // INTENTAR ESTO
    @Named("mapFeatureValuesToTechSheetDto")
    default List<TechSheetDto> mapFeatureValueToTechSheetDto(FeatureValue featureValue) {
        if (featureValue == null) {
            return new ArrayList<>();
        }

        // Convertir un solo FeatureValue en una lista con un solo elemento
        List<TechSheetDto> techSheetDtos = new ArrayList<>();
        techSheetDtos.add(new TechSheetDto(
                featureValue.getFeature().getName(), // Nombre de la característica
                featureValue.getValue() // Valor de la característica
        ));
        return techSheetDtos;
    }
}
// En la entidad Product, el campo productFeature sirve para relacionar un
// producto con la ficha tecnica
// private List<ProductFeature> productFeature = new ArrayList<>();

// Entidad ProductFeature
// public class ProductFeature {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;

// @ManyToOne // Cascada para Feature
// @JoinColumn(name = "product_id", nullable = false)
// private Product product;

// @ManyToOne(fetch = FetchType.LAZY, targetEntity = Feature.class) // Cascada
// para Feature
// @JoinColumn(name = "feature_id", nullable = false)
// private Feature feature;

// @ManyToOne(fetch = FetchType.LAZY, targetEntity = FeatureValue.class)
// @JoinColumn(name = "feature_value_id", nullable = false)
// private FeatureValue featureValue;
// }

// DTO ProductTechSheetDto
// public class ProductTechSheetDto {
// private Long id;
// private List<TechSheetDto> techSheet;
// }

// DTO TechSheetDto
// public class TechSheetDto {
// private String feature;
// private String value;
// }

// ............

// {
// return
// ProductTechSheetDto.builder().id(product.getProductFeature().getClass().getId()).techSheet(product.getProductFeature())
// }

// public ProductTechSheetDto toDto(Product product) {
// return ProductTechSheetDto.builder()
// .id(product.getId())
// .techSheet(product.getTechSheet().stream()
// .map(this::toTechSheetDto)
// .collect(Collectors.toList()))
// .build();
// }

// @Mapping(target = "techSheet", source = "techSheet")
// Product toEntity(ProductTechSheetDto dto);
// private TechSheetDto toTechSheetDto(TechSheet techSheet) {
// return TechSheetDto.builder()
// .feature(techSheet.getFeature())
// .value(techSheet.getValue())
// .build();
// }

// private TechSheet toTechSheetEntity(TechSheetDto dto) {
// TechSheet techSheet = new TechSheet();
// techSheet.setFeature(dto.getFeature());
// techSheet.setValue(dto.getValue());
// return techSheet;
// }

// Explicación:
// 1. @Mapping(target = "id", source = "id") → Mapea directamente el ID del
// Product al DTO.
// 2. @Mapping(target = "techSheet", source = "productFeature", qualifiedByName
// = "mapProductFeatureToTechSheetDto")
// Usa el método mapProductFeatureToTechSheetDto para convertir la lista de
// ProductFeature en List<TechSheetDto>.
// 3. @Named("mapProductFeatureToTechSheetDto")
// Se usa @Named para definir un método auxiliar que convierte la lista.
// Se recorre productFeatures para crear una lista de TechSheetDto, extrayendo
// feature y value de las entidades.
