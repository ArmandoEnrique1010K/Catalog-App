package com.backend.electronic.services;

import java.util.List;

import com.backend.electronic.models.dto.FeatureValueDto;
import com.backend.electronic.models.entities.FeatureValue;

public interface FeatureValueService {

    // Listar todos los valores que corresponden a una caracteristica
    public List<FeatureValueDto> findAllByFeatureId(Long id);

    // Guardar un nuevo valor para la caracteristica
    public FeatureValueDto save(FeatureValue featureValue, Long id);

    // Lista todos los valores que corresponden a una caracteristicas y cuyos
    // valores se encuentren si o si dentro de los productos
    public List<FeatureValueDto> getFeatureValuesByCategoryAndFeature(Long categoryId, Long featureId);

    // TODO: AÑADIR UN SERVICIO PARA VERIFICAR QUE EL VALOR DE LA CARACTERISTICA NO
    // COINCIDA CON UNO EXISTENTE
}
