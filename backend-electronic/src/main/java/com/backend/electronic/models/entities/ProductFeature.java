package com.backend.electronic.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: MODIFICAR ESTO
@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_feature")
public class ProductFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "feature_value_id")
    private FeatureValue featureValue;

    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;

    // Getters and Setters
}
