package com.backend.electronic.services;

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
import com.backend.electronic.validators.CustomValidator;

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

    @Autowired
    private CustomValidator customValidator;

    @Override
    public List<TechSheetDto> getTechSheet(Long productId) {

        List<ProductFeature> productFeatures = productFeatureRepository.findTechSheetByProductId(productId);
        return productFeatures.stream().map(
                techSheetDtoMapper::toListDto)
                .toList();

    }

    @Override
    public ProductTechSheetDto saveTechSheet(Long productId, List<TechSheetDto> techSheet) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // RESULTADO POR CADA UNO Y FINAL
        // ProductFeature result = null;
        // List<ProductFeature> finalResult = new ArrayList<>();

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
                productFeature.setFeature(feature);
                productFeature.setFeatureValue(featureValue);
                productFeatureRepository.save(productFeature);

                // AÑADIR EL RESULTADO
                // finalResult.add(result);
            }

            // System.out.println("Datos recibidos: " + techSheet); // DEPURACIÓN
        }

        // System.out.println(result);

        // Map<String, Object> resultado = new HashMap<>();
        // resultado.put("id", productId);
        // resultado.put("", finalResult);
        // // TODO: ARREGLAR ESTO, POSIBLE SOLUCION???

        // Recuperar las características del producto guardadas
        List<ProductFeature> productFeatures = productFeatureRepository.findTechSheetByProductId(productId);

        // NOTA: SOLAMENTE SE ASIGNA LOS CAMPOS NECESARIOS
        // POSIBLE SOLUCION
        Product result = new Product();
        result.setId(productId);
        result.setProductFeatures(productFeatures);
        // ESPERA UN ARGUMENTO DE TIPO Product
        return productTechSheetDtoMapper.toDto(result);
    }

    // SERVICIO PARA ACTUALIZAR LA FICHA TECNICA
    // SI UN PRODUCTO NO TIENE SU FICHA TECNICA, LO CREA

    // ES POSIBLE SOLUCIONAR EL PROBLEMA CUANDO SE PASA EN LA PETICIÓN ALGUN
    // PAR CUYO FEATURE YA EXISTA. NORMALMENTE TOMA EL VALUE DEL ULTIMO FEATURE PARA
    // ASIGNARLO AL PRODUCTO
    @Transactional
    @Override
    public Optional<ProductTechSheetDto> updateTechSheet(Long productId, List<TechSheetDto> techSheet) {

        // Llama a la validación si un feature se repite en la peticion con valores
        // distintos
        customValidator.validateExistingPairFeatureValue(techSheet);

        // Buscar el producto en la base de datos
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Crear un mapa para almacenar las nuevas características
        Map<String, String> newFeaturesMap = techSheet.stream()
                .collect(Collectors.toMap(
                        TechSheetDto::getFeature,
                        TechSheetDto::getValue,
                        (existingValue, newValue) -> newValue // Si hay duplicados, se actualiza con el último valor
                ));

        // Llama al servicio de eliminación de ficha tecnica antes de continuar.
        deleteObsoleteTechSheet(productId, new ArrayList<>(newFeaturesMap.keySet()));

        // Ahora recargar las características existentes
        List<ProductFeature> existingFeatures = productFeatureRepository.findTechSheetByProductId(productId);

        // Procesar cada característica de la nueva ficha técnica
        for (Map.Entry<String, String> entry : newFeaturesMap.entrySet()) {
            String featureName = entry.getKey();
            String featureValueStr = entry.getValue();

            // Buscar o crear la Feature
            Feature feature = featureRepository.findByName(featureName)
                    .orElseGet(() -> {
                        Feature newFeature = new Feature();
                        newFeature.setName(featureName);
                        newFeature.setStatus(true);
                        return featureRepository.save(newFeature);
                    });

            // Buscar o crear el FeatureValue
            FeatureValue featureValue = featureValueRepository.findByFeatureAndValue(feature, featureValueStr)
                    .orElseGet(() -> {
                        FeatureValue newFeatureValue = new FeatureValue();
                        newFeatureValue.setFeature(feature);
                        newFeatureValue.setValue(featureValueStr);
                        return featureValueRepository.save(newFeatureValue);
                    });

            // Buscar si ya existe una relación `ProductFeature` con este `Feature`
            Optional<ProductFeature> existingProductFeature = existingFeatures.stream()
                    .filter(pf -> pf.getFeature().equals(feature))
                    .findFirst();

            if (existingProductFeature.isPresent()) {
                ProductFeature productFeature = existingProductFeature.get();

                // 📌 Si el `FeatureValue` es diferente, actualizarlo
                if (!productFeature.getFeatureValue().equals(featureValue)) {
                    productFeature.setFeatureValue(featureValue);
                    productFeatureRepository.save(productFeature);
                }

            } else {
                // Si no existe, crear una nueva relación ProductFeature
                ProductFeature productFeature = new ProductFeature();
                productFeature.setProduct(product);
                productFeature.setFeature(feature);
                productFeature.setFeatureValue(featureValue);
                productFeatureRepository.save(productFeature);
            }

        }

        // TODO: DEBERIA RETORNAR LOS DATOS DEL PRODUCTO ACTUALIZADO
        // List<ProductFeature> productFeatures2 =
        // productFeatureRepository.findTechSheetByProductId(productId);
        // Product result = new Product();
        // result.setId(productId);
        // result.setProductFeatures(productFeatures2);
        // return Optional.of(productTechSheetDtoMapper.toDto(result));
        return productRepository.findById(productId)
                .map(productTechSheetDtoMapper::toDto);

    }

    // Servicio para eliminar datos de la ficha tecnica
    @Transactional
    public void deleteObsoleteTechSheet(Long productId, List<String> newFeatureNames) {
        // Obtener las características actuales del producto
        List<ProductFeature> existingFeatures = productFeatureRepository.findTechSheetByProductId(productId);

        // Filtrar las características que ya no están en la nueva ficha técnica
        List<ProductFeature> toRemove = existingFeatures.stream()
                .filter(pf -> !newFeatureNames.contains(pf.getFeature().getName()))
                .collect(Collectors.toList());

        // Si hay características que eliminar, eliminarlas en batch
        if (!toRemove.isEmpty()) {
            productFeatureRepository.deleteAllInBatch(toRemove);
        }
    }

}
