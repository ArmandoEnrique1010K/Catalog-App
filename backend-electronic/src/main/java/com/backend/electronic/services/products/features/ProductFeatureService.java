package com.backend.electronic.services.products.features;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.electronic.models.dto.ProductDetailDto;
import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.entities.Product;
import com.backend.electronic.models.entities.ProductFeature;
import com.backend.electronic.models.requests.TechSheetRequestDto;

public interface ProductFeatureService {
    public List<TechSheetDto> getTechSheet(Long productId);

    public void saveTechSheet(Long productId, List<TechSheetDto> techSheet);

    public void saveTechSheet2(Long productId, List<ProductFeature> techSheet);

}
