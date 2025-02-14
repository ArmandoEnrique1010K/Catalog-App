package com.backend.electronic.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

// Dto para los detalles de un producto
public class ProductDetailDto {
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
}
