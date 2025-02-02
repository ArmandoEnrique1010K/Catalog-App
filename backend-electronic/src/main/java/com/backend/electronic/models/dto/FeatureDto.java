package com.backend.electronic.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureDto {

    // TODO: MODIFICAR ESTO
    private Long idFeature;
    private String nameFeature;
    private Long idValue;
    private String nameValue;
}
