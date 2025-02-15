package com.backend.electronic.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryFeatureNameDto {
    private Long id;
    // private Long idCategory;
    private Long idFeature;
    private String nameFeature;
}
