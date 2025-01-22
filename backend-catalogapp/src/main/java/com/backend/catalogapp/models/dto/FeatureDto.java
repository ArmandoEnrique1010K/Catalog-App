package com.backend.catalogapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureDto {

    private Long idFeature;
    private String nameFeature;
    private Long idValue;
    private String nameValue;
}
