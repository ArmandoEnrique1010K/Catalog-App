package com.backend.catalogapp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id_product;
    private String code;
    private String name;
    private Boolean inOffer;
    private Double price;
    private Double offerPrice;
    // private Boolean status;
    private LocalDateTime lastEditionDate;
    private LocalDateTime createDate;
}
