package com.backend.electronic.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
// Dto para la lista de productos
public class ProductsListDto {
    private Long id;
    private String code;
    private String name;
    private Boolean inOffer;
    private Double currentPrice;
    private Double oldPrice;
    private String nameImage;
    private String brandName;
}
