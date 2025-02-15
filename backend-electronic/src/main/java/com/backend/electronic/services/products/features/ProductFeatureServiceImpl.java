package com.backend.electronic.services.products.features;

import java.util.List;

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

    // REPARAR ESTE SERVICIO
    @Override
    public ProductTechSheetDto saveTechSheet(Long productId, List<TechSheetDto> techSheet) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // RESULTADO FINAL
        ProductFeature result = null;

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
            }

            // System.out.println("Datos recibidos: " + techSheet); // DEPURACIÓN
        }
        return productTechSheetDtoMapper.toDto(result);
    }

    // @Override
    // public void saveTechSheet2(Long productId, List<ProductFeature> techSheet) {
    // Product product = productRepository.findById(productId)
    // .orElseThrow(() -> new IllegalArgumentException("El producto con ID " +
    // productId + " no existe"));

    // for (ProductFeature pf : techSheet) {
    // Feature feature = featureRepository.findById(pf.getFeature().getId())
    // .orElseThrow(() -> new IllegalArgumentException(
    // "La característica con ID " + pf.getFeature().getId() + " no existe"));

    // FeatureValue featureValue =
    // featureValueRepository.findById(pf.getFeatureValue().getId())
    // .orElseGet(() -> {
    // FeatureValue newFeatureValue = new FeatureValue();
    // newFeatureValue.setValue("Nuevo Valor");
    // newFeatureValue.setFeature(feature);
    // return featureValueRepository.save(newFeatureValue);
    // });

    // ProductFeature productFeature = new ProductFeature();
    // productFeature.setProduct(product);
    // productFeature.setFeature(feature);
    // productFeature.setFeatureValue(featureValue);

    // productFeatureRepository.save(productFeature);
    // }
    // }

}
