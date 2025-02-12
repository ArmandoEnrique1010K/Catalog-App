package com.backend.electronic.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.electronic.models.entities.FeatureValue;
import com.backend.electronic.models.entities.Product;
import com.backend.electronic.models.entities.ProductFeature;

public interface ProductFeatureRepository extends JpaRepository<ProductFeature, Long> {

    // ENCUENTRA UNA FICHA TECNICA POR EL PRODUCTO
    @Query("SELECT pf FROM ProductFeature pf " +
            "JOIN pf.featureValue fv " +
            "JOIN fv.feature f " +
            "WHERE pf.product.id = :productId")
    List<ProductFeature> findTechSheetByProductId(@Param("productId") Long productId);

    boolean existsByProductAndFeatureValue(Product product, FeatureValue featureValue);

    Optional<ProductFeature> findByProductAndFeatureValue(Product product, FeatureValue featureValue);

}
