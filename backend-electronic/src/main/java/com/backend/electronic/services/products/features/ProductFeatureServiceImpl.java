package com.backend.electronic.services.products.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.electronic.models.dto.ProductTechSheetDto;
import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.dto.mapper.ProductTechSheetDtoMapper;
import com.backend.electronic.models.dto.mapper.TechSheetDtoMapper;
import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.models.entities.FeatureValue;
import com.backend.electronic.models.entities.Product;
import com.backend.electronic.models.entities.ProductFeature;
import com.backend.electronic.repositories.FeatureRepository;
import com.backend.electronic.repositories.FeatureValueRepository;
import com.backend.electronic.repositories.ProductFeatureRepository;
import com.backend.electronic.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductFeatureServiceImpl implements ProductFeatureService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private FeatureValueRepository featureValueRepository;

    @Autowired
    private ProductFeatureRepository productFeatureRepository;

    @Autowired
    private TechSheetDtoMapper techSheetDtoMapper;

    @Autowired
    private ProductTechSheetDtoMapper productTechSheetDtoMapper;

    @Override
    public List<TechSheetDto> getTechSheet(Long productId) {

        List<ProductFeature> productFeature = productFeatureRepository.findTechSheetByProductId(productId);
        return productFeature.stream().map(
                techSheetDtoMapper::toListDto)
                .toList();

    }

    @Override
    public ProductTechSheetDto saveTechSheet(Long productId, List<TechSheetDto> techSheet) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // RESULTADO POR CADA UNO Y FINAL
        ProductFeature result = null;
        List<ProductFeature> finalResult = new ArrayList<>();

        for (TechSheetDto entry : techSheet) {
            // Buscar si la característica ya existe
            Feature feature = featureRepository.findByName(entry.getFeature())
                    .orElseGet(() -> {
                        Feature newFeature = new Feature();
                        newFeature.setName(entry.getFeature());
                        return featureRepository.save(newFeature);
                    });

            // Buscar si el valor ya existe para esa característica
            FeatureValue featureValue = featureValueRepository.findByFeatureAndValue(feature, entry.getValue())
                    .orElseGet(() -> {
                        FeatureValue newValue = new FeatureValue();
                        newValue.setFeature(feature);
                        newValue.setValue(entry.getValue());
                        return featureValueRepository.save(newValue);
                    });

            // Verificar si ya existe la relación producto - valor de característica
            boolean exists = productFeatureRepository.existsByProductAndFeatureValue(product, featureValue);
            if (!exists) {
                ProductFeature productFeature = new ProductFeature();
                productFeature.setProduct(product);
                productFeature.setFeature(feature); // NO CONFIES EN CHATGPT, SIEMPRE MODIFICA EL CODIGO GENERADO CON IA
                productFeature.setFeatureValue(featureValue);
                result = productFeatureRepository.save(productFeature);

                // AÑADIR EL RESULTADO
                finalResult.add(result);
            }

            // System.out.println("Datos recibidos: " + techSheet); // DEPURACIÓN
        }

        // System.out.println(result);

        return productTechSheetDtoMapper.toDto(product);
    }

    // TODO: CONSTRUIR ESTE SERVICIO PARA ACTUALIZAR LA FICHA TECNICA

    @Transactional
    @Override
    public Optional<ProductTechSheetDto> updateTechSheet(Long productId, List<TechSheetDto> techSheet) {
        // Buscar el producto en la base de datos
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Obtener las características actuales del producto
        List<ProductFeature> existingFeatures = productFeatureRepository.findTechSheetByProductId(productId);

        // Crear un mapa para evitar duplicados y asegurar la última actualización

        // Crear un mapa para almacenar las nuevas características
        Map<String, String> newFeaturesMap = new HashMap<>();

        // Procesar el JSON y manejar duplicados (actualizar el valor si la clave ya
        // existe)
        for (TechSheetDto dto : techSheet) {
            newFeaturesMap.put(dto.getFeature(), dto.getValue());
        }

        // MAP evita duplicados
        // Map<String, String> newFeaturesMap = techSheet.stream()
        // .collect(Collectors.toMap(
        // TechSheetDto::getFeature,
        // TechSheetDto::getValue,
        // (existingValue, newValue) -> newValue // Si hay duplicados, se actualiza con
        // el último valor
        // ));

        // // Eliminar las características que ya no están en la nueva ficha técnica
        // existingFeatures.forEach(pf -> {
        // String featureName = pf.getFeature().getName();
        // if (!newFeaturesMap.containsKey(featureName)) {
        // productFeatureRepository.delete(pf); // Eliminar la relación si la
        // característica ya no está
        // }
        // });
        // Eliminar las características que ya no están en la nueva ficha técnica
        existingFeatures.forEach(pf -> {
            String featureName = pf.getFeature().getName();
            if (!newFeaturesMap.containsKey(featureName)) {
                productFeatureRepository.delete(pf); // Eliminar la relación si la característica ya no está
            }
        });

        // Procesar cada característica de la nueva ficha técnica
        for (Map.Entry<String, String> entry : newFeaturesMap.entrySet()) {
            String featureName = entry.getKey();
            String featureValueStr = entry.getValue();

            // Buscar la Feature existente
            // TODO: En el caso de que no exista el feature, lo debe crear uno nuevo

            // Buscar o crear la Feature si no existe
            Feature feature = featureRepository.findByName(featureName)
                    .orElseGet(() -> {
                        Feature newFeature = new Feature();
                        newFeature.setName(featureName);
                        newFeature.setStatus(true); // Asignar un valor predeterminado
                        return featureRepository.save(newFeature);
                    });

            // Buscar el FeatureValue existente para la Feature dada
            // Buscar o crear el FeatureValue si no existe
            FeatureValue featureValue = featureValueRepository.findByFeatureAndValue(feature, featureValueStr)
                    .orElseGet(() -> {
                        FeatureValue newFeatureValue = new FeatureValue();
                        newFeatureValue.setFeature(feature); // Asignar la Feature
                        newFeatureValue.setValue(featureValueStr);
                        return featureValueRepository.save(newFeatureValue);
                    });

            // Buscar si ya existe una relación ProductFeature para esta característica
            Optional<ProductFeature> existingProductFeature = existingFeatures.stream()
                    .filter(pf -> pf.getFeature().getName().equals(featureName))
                    .findFirst();

            // Verifica la relación
            if (existingProductFeature.isPresent()) {
                // Si existe, actualizar el FeatureValue
                ProductFeature productFeature = existingProductFeature.get();
                productFeature.setFeatureValue(featureValue);
                productFeatureRepository.save(productFeature);
            } else {
                // Si no existe, crear una nueva relación ProductFeature
                ProductFeature productFeature = new ProductFeature();
                productFeature.setProduct(product);
                productFeature.setFeature(feature);
                productFeature.setFeatureValue(featureValue);
                productFeatureRepository.save(productFeature);
            }

        }

        return Optional.of(productTechSheetDtoMapper.toDto(product));
    }

}
