package com.backend.electronic.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryFeatureDto {
    private Long id;
    private Long idCategory;
    private Long idFeature;
}
