package com.backend.electronic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.electronic.models.entities.FeatureValue;
import java.util.List;

public interface FeatureValueRepository extends JpaRepository<FeatureValue, Long> {

    @Query("SELECT v FROM FeatureValue v JOIN FETCH v.feature WHERE v.feature.id = :id")
    List<FeatureValue> findByFeatureId(Long id);
}
