package com.backend.catalogapp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.backend.catalogapp.dto.FeatureDto;
import com.backend.catalogapp.dto.ProductDto;
import com.backend.catalogapp.entities.Product;

public class ProductDtoMapper {

    private Product product;

    private ProductDtoMapper() {

    };

    public static ProductDtoMapper builder() {
        return new ProductDtoMapper();
    }

    public ProductDtoMapper setProduct(Product product) {
        this.product = product;
        return this;
    }

    public ProductDto build() {
        if (product == null) {
            throw new RuntimeException("Debe pasar la entidad product");
        }

        // TODO: INVESTIGAR COMO OBTENER LA LISTA DE CARACTERISTICAS
        List<FeatureDto> features = product.getCategory().getFeature().stream()
                .map(feature -> new FeatureDto(feature.getId(), feature.getName(), feature.getValue().getId(),
                        feature.getValue().getName())) // Adjust according to your FeatureDto class
                .collect(Collectors.toList());

        return new ProductDto(
                this.product.getId(),
                product.getBrand().getName(),
                product.getCode(),
                product.getInOffer(),
                product.getPrice(),
                product.getOfferPrice(),
                product.getDescription(),
                product.getUpdatedAt(),
                product.getCreatedAt(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                // private List<FeatureDto> featureDto;
                features,
                product.getBrand().getId(),
                product.getBrand().getName(),
                product.getImage().getId(),
                product.getImage().getName(),
                product.getImage().getFile());
    }

}
