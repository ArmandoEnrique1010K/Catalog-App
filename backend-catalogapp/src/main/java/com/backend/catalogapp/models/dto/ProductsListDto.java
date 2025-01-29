package com.backend.catalogapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsListDto {
    private Long id;
    private String code;
    private String name;
    private Boolean inOffer;
    private Double price;
    private Double offerPrice;
    private Long idImage;
    private String nameImage;
}
