package com.backend.electronic.models.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.backend.electronic.models.dto.ProductTechSheetDto;
import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.entities.Product;

@Component
@Mapper(componentModel = "spring")
// TODO: REPARAR ESTO
public interface ProductTechSheetDtoMapper {
    public ProductTechSheetDto toDto(Product product) {
        return ProductTechSheetDto.builder()
                .id(product.getId())
                .techSheet(product.getTechSheet().stream()
                        .map(this::toTechSheetDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Product toEntity(ProductTechSheetDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setTechSheet(dto.getTechSheet().stream()
                .map(this::toTechSheetEntity)
                .collect(Collectors.toList()));
        return product;
    }

    private TechSheetDto toTechSheetDto(TechSheet techSheet) {
        return TechSheetDto.builder()
                .feature(techSheet.getFeature())
                .value(techSheet.getValue())
                .build();
    }

    private TechSheet toTechSheetEntity(TechSheetDto dto) {
        TechSheet techSheet = new TechSheet();
        techSheet.setFeature(dto.getFeature());
        techSheet.setValue(dto.getValue());
        return techSheet;
    }

}
