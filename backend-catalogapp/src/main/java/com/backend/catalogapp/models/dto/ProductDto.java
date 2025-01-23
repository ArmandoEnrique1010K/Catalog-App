package com.backend.catalogapp.models.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long idProduct;
    private String codeProduct;
    private String nameProduct;
    private Boolean inOfferProduct;
    private Double priceProduct;
    private Double offerPriceProduct;
    // private Boolean status;
    private String descriptionProduct;
    private LocalDateTime updatedAtProduct;
    private LocalDateTime createdAtProduct;

    // Categoria
    private Long idCategory;
    private String nameCategory;

    // Caracteristicas
    // private List<FeatureDto> featureDto;
    // private Feature feature;
    private List<FeatureDto> features;

    // Marca
    private Long idBrand;
    private String nameBrand;

    // Imagen
    private Long idImage;
    private String nameImage;
    private MultipartFile fileImage;
}
