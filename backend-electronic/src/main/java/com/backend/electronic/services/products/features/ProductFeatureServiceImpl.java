package com.backend.electronic.services.products.features;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.electronic.models.dto.ProductDetailDto;
import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.dto.mapper.TechSheetDtoMapper;
import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.models.entities.FeatureValue;
import com.backend.electronic.models.entities.Product;
import com.backend.electronic.models.entities.ProductFeature;
import com.backend.electronic.models.requests.TechSheetRequestDto;
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

    @Override
    public List<TechSheetDto> getTechSheet(Long productId) {

        List<ProductFeature> productFeature = productFeatureRepository.findTechSheetByProductId(productId);
        return productFeature.stream().map(
                techSheetDtoMapper::toListDto)
                .toList();

    }

    @Override
    public void saveTechSheet(TechSheetRequestDto request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        for (TechSheetDto entry : request.getTechSheet()) {
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
                productFeature.setFeatureValue(featureValue);
                productFeatureRepository.save(productFeature);
            }
        }
    }

}
