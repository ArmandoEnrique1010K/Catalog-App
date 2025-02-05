package com.backend.electronic.services.features.values;

import java.util.List;

import com.backend.electronic.models.dto.FeatureValueDto;
import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.models.entities.FeatureValue;

public interface FeatureValueService {

    // Listar todos los valores que corresponden a una caracteristica
    public List<FeatureValueDto> findAllByFeatureId(Long id);

    // Guardar un nuevo valor para la caracteristica
    public FeatureValueDto save(FeatureValue featureValue, Long id);

    // TODO: AÑADIR UN SERVICIO PARA VERIFICAR QUE EL VALOR DE LA CARACTERISTICA NO
    // COINCIDA CON UNO EXISTENTE
}
