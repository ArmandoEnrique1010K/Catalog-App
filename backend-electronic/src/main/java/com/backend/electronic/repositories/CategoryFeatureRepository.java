package com.backend.electronic.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.electronic.models.entities.Category;
import com.backend.electronic.models.entities.CategoryFeature;
import com.backend.electronic.models.entities.Feature;

public interface CategoryFeatureRepository extends JpaRepository<CategoryFeature, Long> {

    // LISTAR LAS CARACTERISTICAS POR UNA CATEGORIA
    @Query("SELECT cf FROM CategoryFeature cf JOIN FETCH cf.category JOIN FETCH cf.feature WHERE cf.category.id = :id")
    List<CategoryFeature> findByCategoryId(Long id);

    // ENCUENTRA SI LA CATEGORIA Y LA CARACTESTICA YA SE ENCUENTRA EN LA BASE DE
    // DATOS
    Optional<CategoryFeature> findByCategoryAndFeature(Category category, Feature feature);

}
