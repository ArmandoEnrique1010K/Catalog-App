package com.backend.electronic.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ESTE DTO REPRESENTA UNA FICHA TECNICA
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechSheetDto {
    private String feature;
    private String value;
}
