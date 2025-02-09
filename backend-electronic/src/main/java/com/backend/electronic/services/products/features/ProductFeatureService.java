package com.backend.electronic.services.products.features;

import java.util.List;

import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.entities.ProductFeature;

public interface ProductFeatureService {
    public List<TechSheetDto> getTechSheet(Long productId);

    public void saveTechSheet(Long productId, List<TechSheetDto> techSheet);

    public void saveTechSheet2(Long productId, List<ProductFeature> techSheet);

}
