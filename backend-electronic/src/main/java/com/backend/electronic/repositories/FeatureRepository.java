package com.backend.electronic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.electronic.models.entities.Feature;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findByStatusTrue();

}
