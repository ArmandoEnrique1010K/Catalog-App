package com.backend.electronic.models.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDetailTechSheetDto {
    private Long id;
    private String code;
    private String name;
    private Boolean inOffer;
    private Double currentPrice;
    private Double oldPrice;
    private String description;
    private Long idCategory;
    private Long idBrand;
    private String nameImage;

    // CAMPOS PARA FICHA TECNICA (variante de ProductTechSheetDto)
    // private String feature;
    // private String value;
    // private Long id;
    private Map<String, String> techSheet;

}
