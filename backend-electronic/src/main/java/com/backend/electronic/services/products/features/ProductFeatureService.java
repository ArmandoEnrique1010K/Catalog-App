package com.backend.electronic.services.products.features;

import java.util.List;
import java.util.Optional;

import com.backend.electronic.models.dto.ProductTechSheetDto;
import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.entities.ProductFeature;

public interface ProductFeatureService {
    public List<TechSheetDto> getTechSheet(Long productId);

    public ProductTechSheetDto saveTechSheet(Long productId, List<TechSheetDto> techSheet);

    public Optional<ProductTechSheetDto> updateTechSheet(Long productId, List<TechSheetDto> techSheet);
    // public void saveTechSheet2(Long productId, List<ProductFeature> techSheet);

}
