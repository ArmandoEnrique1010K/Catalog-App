package com.backend.electronic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.models.entities.FeatureValue;
import java.util.List;
import java.util.Optional;

public interface FeatureValueRepository extends JpaRepository<FeatureValue, Long> {

        // LISTA TODOS LOS VALORES DE UNA CARACTERISTICA POR SU ID
        @Query("SELECT v FROM FeatureValue v JOIN FETCH v.feature WHERE v.feature.id = :id")
        List<FeatureValue> findByFeatureId(Long id);

        // Encuentra una caracteristica por su nombre
        Optional<FeatureValue> findByValue(String value);

        // TODO: EXPERIMENTAL, ENCUENTRA LA PRIMERA CARACTERISTICA POR SU NOMBRE
        // Optional<FeatureValue> findFirstByValue(String value);

        // LISTA TODOS LOS VALORES DE UNA CARACTERISTICA POR SU ID Y TODAS LAS
        // CARACTERISTICAS DISTINTAS QUE CORRESPONDEN A LA CATEGORIA
        @Query("SELECT DISTINCT fv FROM FeatureValue fv " +
                        "JOIN fv.productFeatures pf " +
                        "JOIN pf.product p " +
                        "WHERE p.category.id = :categoryId " +
                        "AND fv.feature.id = :featureId")
        List<FeatureValue> findFeatureValuesByCategoryAndFeature(
                        @Param("categoryId") Long categoryId,
                        @Param("featureId") Long featureId);

        // Encuentra un valor de una caracteristica por su nombre de valor y
        // caracteristica
        Optional<FeatureValue> findByFeatureAndValue(Feature feature, String value);

        // TODO: EXPERIMENTAL, ENCUENTRA LA PRIMERA CARACTERISTICA POR SU NOMBRE Y VALOR
        Optional<FeatureValue> findFirstByFeatureAndValue(Feature feature, String value);

}
